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

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//import java.lang.annotation.Repeatable;  //TODO 在jdk7环境下编译
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation specifies an arbitrary activation property to be used by a
 * callback method on a JMS message-driven bean must use the specified message
 * selector. It may be specified either on the callback method or on the
 * message-driven bean class.
 * <p>
 * If this annotation is specified on a method of a message-driven bean class
 * then that method must also be annotated with {@code QueueListener} or
 * {@code TopicListener}. If it is not then deployment will fail.
 * <p>
 * If this annotation is specified on the message-driven bean class then at
 * least one method must be annotated with {@code QueueListener} or
 * {@code TopicListener}. If no method is annotated with {@code QueueListener}
 * or {@code TopicListener} then deployment will fail.
 * <p>
 * If this annotation is used to specify the same property on both a method of a
 * message-driven bean class and on the message-driven bean class itself then
 * deployment will fail.
 * <p>
 * Multiple {@code JMSListenerProperty} annotations may be used to set multiple
 * properties on the same callback method.
 * 
 * @version JMS 2.1
 * @since JMS 2.1
 * 
 */
//@Repeatable(ListenerProperties.class)  //TODO 在jdk7环境下编译
@Retention(RUNTIME)
@Target({ METHOD })
public @interface ListenerProperty {

	/**
	 * Name of the activation property
	 */
	String name();

	/**
	 * Value of the activation property
	 */
	String value();

}