/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1997-2003 The Apache Software Foundation. All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *    "This product includes software developed by the
 *    Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software
 *    itself, if and wherever such third-party acknowledgments
 *    normally appear.
 *
 * 4. The names "Jakarta", "Avalon", and "Apache Software Foundation"
 *    must not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation. For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package org.apache.avalon.framework.component;

/**
 * This interface identifies classes that can be used as <code>Components</code>
 * by a <code>Composable</code>.
 *
 * <p>
 * The contract surrounding the <code>Component</code> is that it is
 * used, but not a user.  When a class implements this interface, it
 * is stating that other entities may use that class.
 * </p>
 *
 * <p>
 * A <code>Component</code> is the basic building block of the Avalon Framework.
 * When a class implements this interface, it allows itself to be
 * managed by a <code>ComponentManager</code> and used by an outside
 * element called a <code>Composable</code>.  The <code>Composable</code>
 * must know what type of <code>Component</code> it is accessing, so
 * it will re-cast the <code>Component</code> into the type it needs.
 * </p>
 *
 * <p>
 * In order for a <code>Component</code> to be useful you must either
 * extend this interface, or implement this interface in conjunction
 * with one that actually has methods.  The new interface is the contract
 * with the <code>Composable</code> that this is a particular type of
 * component, and as such it can perform those functions on that type
 * of component.
 * </p>
 *
 * <p>
 * For example, we want a component that performs a logging function
 * so we extend the <code>Component</code> to be a <code>LoggingComponent</code>.
 * </p>
 *
 * <pre>
 *   interface LoggingComponent
 *       extends Component
 *   {
 *       log(String message);
 *   }
 * </pre>
 *
 * <p>
 * Now all <code>Composable</code>s that want to use this type of component,
 * will re-cast the <code>Component</code> into a <code>LoggingComponent</code>
 * and the <code>Composable</code> will be able to use the <code>log</code>
 * method.
 * </p>
 *
 * <p>
 *  <span style="color: red">Deprecated: </span><i>
 *    Deprecated without replacement. Should only be used while migrating away
 *    from a system based on Composable/ComponentManager.  A generic <code>java.lang.Object</code>
 *    can be used as a replacement.
 *  </i>
 * </p>
 *
 * @author <a href="mailto:dev@avalon.apache.org">Avalon Development Team</a>
 * @version CVS $Revision: 1.14 $ $Date: 2003/02/11 15:58:38 $
 */
public interface Component
{
}
