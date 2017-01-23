/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package javax.jms;

/**
 * This is a marker interface that may be implemented by a message-driven bean
 * class to specify that it is used to consume JMS messages. It is an
 * alternative to implementing the MessageListener interface.
 * <p>
 * A JMS message-driven bean that implements the {@code JMSMessageDrivenBean}
 * marker interface must not also implement the {@code MessageListener}
 * interface.
 * <p>
 * If the MDB implements this interface then each callback method must be
 * annotated with one of {@code JMSQueueListener},
 * {@code JMSNonDurableTopicListener} or {@code JMSDurableTopicListener}.
 * <p>
 * 
 * @see JMSMessageDrivenBean
 * @see TopicListener
 * @see DurableSubscription
 * @see QueueListener
 * 
 * @version JMS 2.1
 * @since JMS 2.1
 * 
 */

public interface JMSMessageDrivenBean {

}
