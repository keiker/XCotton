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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that a callback method parameter must be set to the specified
 * message header value. This annotation may be applied to parameters on a
 * callback method on a JMS message-driven bean that has been annotated with the
 * {@code JMSQueueListener} , {@code JMSNonDurableTopicListener} or
 * {@code JMSDurableTopicListener} annotation.
 * <p>
 * The parameter type must match the header type as shown in the following
 * table. If it does not then deployment will fail.
 * <p>
 * <pre>
 * +-------------------+------------------------------------------------+
 * | Annotation                                     | Parameter         |
 * |                                                | type              |
 * +-------------------+------------------------------------------------+
 * | @MessageHeader(Header.JMSCorrelationID)        | String            |     
 * | @MessageHeader(Header.JMSCorrelationIDAsBytes) | byte[]            | 
 * | @MessageHeader(Header.JMSDeliveryMode)         | Integer or int    | 
 * | @MessageHeader(Header.JMSDeliveryTime)         | Long or long      | 
 * | @MessageHeader(Header.JMSDestination)          | Destination       | 
 * | @MessageHeader(Header.JMSExpiration)           | Long or long      | 
 * | @MessageHeader(Header.JMSMessageID)            | String            | 
 * | @MessageHeader(Header.JMSPriority)             | Integer or int    |
 * | @MessageHeader(Header.JMSRedelivered)          | Boolean or boolean| 
 * | @MessageHeader(Header.JMSReplyTo)              | Destination       | 
 * | @MessageHeader(Header.JMSTimestamp)            | Long or long      | 
 * | @MessageHeader(Header.JMSType)                 | String            | 
 * +-------------------+------------------------------------------------+
 * </pre>
 * 
 * @version JMS 2.1
 * @since JMS 2.1
 * 
 * @see MessageProperty
 * 
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageHeader {

    /**
     * Specifies the header field to be used
     */
    Header value();

    public enum Header {
        JMSCorrelationID, JMSCorrelationIDAsBytes, JMSDeliveryMode, JMSDeliveryTime, JMSDestination, JMSExpiration, JMSMessageID, JMSPriority, JMSRedelivered, JMSReplyTo, JMSTimestamp, JMSType
    }

}
