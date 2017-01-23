/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jctools.queues;

import static org.jctools.util.UnsafeAccess.UNSAFE;
import static org.jctools.util.UnsafeRefArrayAccess.lvElement;
import static org.jctools.util.UnsafeRefArrayAccess.soElement;

import org.jctools.util.Pow2;
import org.jctools.util.UnsafeRefArrayAccess;

abstract class SpscArrayQueueColdField<E> extends ConcurrentCircularArrayQueue<E> {
    static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096);
    protected final int lookAheadStep;
    public SpscArrayQueueColdField(int capacity) {
        super(capacity);
        lookAheadStep = Math.min(capacity/4, MAX_LOOK_AHEAD_STEP);
    }
}
abstract class SpscArrayQueueL1Pad<E> extends SpscArrayQueueColdField<E> {
    long p01, p02, p03, p04, p05, p06, p07;
    long p10, p11, p12, p13, p14, p15, p16, p17;

    public SpscArrayQueueL1Pad(int capacity) {
        super(capacity);
    }
}

abstract class SpscArrayQueueProducerFields<E> extends SpscArrayQueueL1Pad<E> {
    protected final static long P_INDEX_OFFSET;
    static {
        try {
            P_INDEX_OFFSET =
                UNSAFE.objectFieldOffset(SpscArrayQueueProducerFields.class.getDeclaredField("producerIndex"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
    protected long producerIndex;
    protected long producerLimit;

    public SpscArrayQueueProducerFields(int capacity) {
        super(capacity);
    }
}

abstract class SpscArrayQueueL2Pad<E> extends SpscArrayQueueProducerFields<E> {
    long p01, p02, p03, p04, p05, p06, p07;
    long p10, p11, p12, p13, p14, p15, p16, p17;

    public SpscArrayQueueL2Pad(int capacity) {
        super(capacity);
    }
}

abstract class SpscArrayQueueConsumerField<E> extends SpscArrayQueueL2Pad<E> {
    protected long consumerIndex;
    protected final static long C_INDEX_OFFSET;
    static {
        try {
            C_INDEX_OFFSET =
                UNSAFE.objectFieldOffset(SpscArrayQueueConsumerField.class.getDeclaredField("consumerIndex"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
    public SpscArrayQueueConsumerField(int capacity) {
        super(capacity);
    }
}


/**
 * A Single-Producer-Single-Consumer queue backed by a pre-allocated buffer.
 * <p>
 * This implementation is a mashup of the <a href="http://sourceforge.net/projects/mc-fastflow/">Fast Flow</a>
 * algorithm with an optimization of the offer method taken from the <a
 * href="http://staff.ustc.edu.cn/~bhua/publications/IJPP_draft.pdf">BQueue</a> algorithm (a variation on Fast
 * Flow), and adjusted to comply with Queue.offer semantics with regards to capacity.<br>
 * For convenience the relevant papers are available in the resources folder:<br>
 * <i>2010 - Pisa - SPSC Queues on Shared Cache Multi-Core Systems.pdf<br>
 * 2012 - Junchang- BQueue- Efﬁcient and Practical Queuing.pdf <br>
 * </i> This implementation is wait free.
 *
 * @author nitsanw
 *
 * @param <E>
 */
public class SpscArrayQueue<E> extends SpscArrayQueueConsumerField<E>  implements QueueProgressIndicators {
    long p01, p02, p03, p04, p05, p06, p07;
    long p10, p11, p12, p13, p14, p15, p16, p17;
    public SpscArrayQueue(final int capacity) {
        super(Math.max(Pow2.roundToPowerOfTwo(capacity), 4));
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is correct for single producer thread use only.
     */
    @Override
    public boolean offer(final E e) {
        if (null == e) {
            throw new NullPointerException();
        }
        // local load of field to avoid repeated loads after volatile reads
        final E[] buffer = this.buffer;
        final long mask = this.mask;
        final long producerIndex = this.producerIndex;

        if (producerIndex >= producerLimit &&
                !offerSlowPath(buffer, mask, producerIndex)) {
            return false;
        }
        final long offset = calcElementOffset(producerIndex, mask);

        soElement(buffer, offset, e); // StoreStore
        soProducerIndex(producerIndex + 1); // ordered store -> atomic and ordered for size()
        return true;
    }

    private boolean offerSlowPath(final E[] buffer, final long mask, final long producerIndex) {
        final int lookAheadStep = this.lookAheadStep;
        if (null == lvElement(buffer, calcElementOffset(producerIndex + lookAheadStep, mask))) {// LoadLoad
            producerLimit = producerIndex + lookAheadStep;
        }
        else{
            final long offset = calcElementOffset(producerIndex, mask);
            if (null != lvElement(buffer, offset)){
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is correct for single consumer thread use only.
     */
    @Override
    public E poll() {
        final long consumerIndex = this.consumerIndex;
        final long offset = calcElementOffset(consumerIndex);
        // local load of field to avoid repeated loads after volatile reads
        final E[] buffer = this.buffer;
        final E e = lvElement(buffer, offset);// LoadLoad
        if (null == e) {
            return null;
        }
        soElement(buffer, offset, null);// StoreStore
        soConsumerIndex(consumerIndex + 1); // ordered store -> atomic and ordered for size()
        return e;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is correct for single consumer thread use only.
     */
    @Override
    public E peek() {
        return UnsafeRefArrayAccess.lvElement(buffer, calcElementOffset(consumerIndex));
    }

    private void soProducerIndex(long v) {
        UNSAFE.putOrderedLong(this, P_INDEX_OFFSET, v);
    }

    private void soConsumerIndex(long v) {
        UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, v);
    }

    public final long lvProducerIndex() {
        return UNSAFE.getLongVolatile(this, P_INDEX_OFFSET);
    }

    public final long lvConsumerIndex() {
        return UNSAFE.getLongVolatile(this, C_INDEX_OFFSET);
    }

	@Override
	public boolean relaxedOffer(E message) {
		return offer(message);
	}

	@Override
	public E relaxedPoll() {
		return poll();
	}

	@Override
	public E relaxedPeek() {
		return peek();
	}

    @Override
    public int drain(final Consumer<E> c) {
        return drain(c, capacity());
    }

    @Override
    public int fill(final Supplier<E> s) {
        return fill(s, capacity());
    }

    @Override
    public int drain(final Consumer<E> c, final int limit) {
        final E[] buffer = this.buffer;
        final long mask = this.mask;
        final long consumerIndex = this.consumerIndex;

        for (int i = 0; i < limit; i++) {
            final long index = consumerIndex + i;
            final long offset = calcElementOffset(index, mask);
            final E e = lvElement(buffer, offset);// LoadLoad
            if (null == e) {
                return i;
            }
            soElement(buffer, offset, null);// StoreStore
            soConsumerIndex(index + 1); // ordered store -> atomic and ordered for size()
            c.accept(e);
        }
        return limit;
    }

    @Override
    public int fill(final Supplier<E> s, final int limit) {
        final E[] buffer = this.buffer;
        final long mask = this.mask;
        final int lookAheadStep = this.lookAheadStep;
        final long producerIndex = this.producerIndex;

        for (int i = 0; i < limit; i++) {
            final long index = producerIndex + i;
            final long lookAheadElementOffset = calcElementOffset(index + lookAheadStep, mask);
            if (null == lvElement(buffer, lookAheadElementOffset)) {// LoadLoad
                int lookAheadLimit = Math.min(lookAheadStep, limit - i);
                for (int j = 0; j < lookAheadLimit; j++) {
                    final long offset = calcElementOffset(index + j, mask);
                    soElement(buffer, offset, s.get()); // StoreStore
                    soProducerIndex(index + j + 1); // ordered store -> atomic and ordered for size()
                }
                i += lookAheadLimit - 1;
            }
            else {
                final long offset = calcElementOffset(index, mask);
                if (null != lvElement(buffer, offset)){
                    return i;
                }
                soElement(buffer, offset, s.get()); // StoreStore
                soProducerIndex(index + 1); // ordered store -> atomic and ordered for size()
            }

        }
        return limit;
    }

    @Override
    public void drain(final Consumer<E> c, final WaitStrategy w, final ExitCondition exit) {
        final E[] buffer = this.buffer;
        final long mask = this.mask;
        long consumerIndex = this.consumerIndex;

        int counter = 0;
        while (exit.keepRunning()) {
            for (int i = 0; i < 4096; i++) {
                final long offset = calcElementOffset(consumerIndex, mask);
                final E e = lvElement(buffer, offset);// LoadLoad
                if (null == e) {
                    counter = w.idle(counter);
                    continue;
                }
                consumerIndex++;
                counter = 0;
                soElement(buffer, offset, null);// StoreStore
                soConsumerIndex(consumerIndex); // ordered store -> atomic and ordered for size()
                c.accept(e);
            }
        }
    }

    @Override
    public void fill(final Supplier<E> s, final WaitStrategy w, final ExitCondition e) {
        final E[] buffer = this.buffer;
        final long mask = this.mask;
        final int lookAheadStep = this.lookAheadStep;
        long producerIndex = this.producerIndex;
        int counter = 0;
        while (e.keepRunning()) {
            final long lookAheadElementOffset = calcElementOffset(producerIndex + lookAheadStep, mask);
            if (null == lvElement(buffer, lookAheadElementOffset)) {// LoadLoad
                for (int j = 0; j < lookAheadStep; j++) {
                    final long offset = calcElementOffset(producerIndex, mask);
                    producerIndex++;
                    soElement(buffer, offset, s.get()); // StoreStore
                    soProducerIndex(producerIndex); // ordered store -> atomic and ordered for size()
                }
            }
            else {
                final long offset = calcElementOffset(producerIndex, mask);
                if (null != lvElement(buffer, offset)){// LoadLoad
                    counter = w.idle(counter);
                    continue;
                }
                producerIndex++;
                counter=0;
                soElement(buffer, offset, s.get()); // StoreStore
                soProducerIndex(producerIndex); // ordered store -> atomic and ordered for size()
            }
        }
    }
}
