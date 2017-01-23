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

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.jctools.util.Pow2.roundToPowerOfTwo;

import org.jctools.util.Pow2;

abstract class MpscChunkedArrayQueueColdProducerFields<E> extends BaseMpscLinkedArrayQueue<E> {
    protected final long maxQueueCapacity;
    public MpscChunkedArrayQueueColdProducerFields(int initialCapacity, int maxCapacity) {
        super(initialCapacity);
        if (maxCapacity < 4) {
            throw new IllegalArgumentException("Max capacity must be 4 or more");
        }
        if (Pow2.roundToPowerOfTwo(initialCapacity) >= Pow2.roundToPowerOfTwo(maxCapacity)) {
            throw new IllegalArgumentException(
                    "Initial capacity cannot exceed maximum capacity(both rounded up to a power of 2)");
        }
        maxQueueCapacity = ((long)Pow2.roundToPowerOfTwo(maxCapacity)) << 1;
    }
}
/**
 * An MPSC array queue which starts at <i>initialCapacity</i> and grows to <i>maxCapacity</i> in linked chunks
 * of the initial size. The queue grows only when the current buffer is full and elements are not copied on
 * resize, instead a link to the new buffer is stored in the old buffer for the consumer to follow.<br>
 *
 * @param <E>
 */
public class MpscChunkedArrayQueue<E> extends MpscChunkedArrayQueueColdProducerFields<E> {
    long p0, p1, p2, p3, p4, p5, p6, p7;
    long p10, p11, p12, p13, p14, p15, p16, p17;

    public MpscChunkedArrayQueue(int maxCapacity) {
        super(max(2, min(1024, roundToPowerOfTwo(maxCapacity / 8))), maxCapacity);
    }

    /**
     * @param initialCapacity the queue initial capacity. If chunk size is fixed this will be the chunk size.
     *        Must be 2 or more.
     * @param maxCapacity the maximum capacity will be rounded up to the closest power of 2 and will be the
     *        upper limit of number of elements in this queue. Must be 4 or more and round up to a larger
     *        power of 2 than initialCapacity.
     */
    public MpscChunkedArrayQueue(int initialCapacity, int maxCapacity) {
        super(initialCapacity, maxCapacity);
    }
    
    /**
     * @param initialCapacity the queue initial capacity. If chunk size is fixed this will be the chunk size.
     *        Must be 2 or more.
     * @param maxCapacity the maximum capacity will be rounded up to the closest power of 2 and will be the
     *        upper limit of number of elements in this queue. Must be 4 or more and round up to a larger
     *        power of 2 than initialCapacity.
     * @param fixedChunkSize not used
     */
    @Deprecated
    public MpscChunkedArrayQueue(int initialCapacity, int maxCapacity, boolean fixedChunkSize) {
        super(initialCapacity, maxCapacity);
    }

    @Override
    protected long availableInQueue(long pIndex, long cIndex) {
        return maxQueueCapacity - (pIndex - cIndex);
    }

    @Override
    public int capacity() {
        return (int) (maxQueueCapacity/2);
    }

    @Override
    protected int getNextBufferSize(E[] buffer) {
        return buffer.length;
    }

    @Override
    protected long getCurrentBufferCapacity(long mask) {
        return mask;
    }
}
