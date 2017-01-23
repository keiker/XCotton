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
package org.apache.avalon.framework.service;

/**
 * A <code>ServiceManager</code> selects <code>Object</code>s based on a
 * role.  The contract is that all the <code>Object</code>s implement the
 * differing roles and there is one <code>Object</code> per role.  If you
 * need to select on of many <code>Object</code>s that implement the same
 * role, then you need to use a <code>ServiceSelector</code>.  Roles are
 * usually the full interface name.
 *
 * A role is better understood by the analogy of a play.  There are many
 * different roles in a script.  Any actor or actress can play any given part
 * and you get the same results (phrases said, movements made, etc.).  The exact
 * nuances of the performance is different.
 *
 * Below is a list of things that might be considered the different roles:
 *
 * <ul>
 *   <li> InputAdapter and OutputAdapter</li>
 *   <li> Store and Spool</li>
 * </ul>
 *
 * The <code>ServiceManager</code> does not specify the methodology of
 * getting the <code>Object</code>, merely the interface used to get it.
 * Therefore the <code>ServiceManager</code> can be implemented with a
 * factory pattern, an object pool, or a simple Hashtable.
 *
 * @author <a href="mailto:dev@avalon.apache.org">Avalon Development Team</a>
 * @version CVS $Revision: 1.14 $ $Date: 2003/02/11 15:58:42 $
 * @see org.apache.avalon.framework.service.Serviceable
 * @see org.apache.avalon.framework.service.ServiceSelector
 */
public interface ServiceManager
{
    /**
     * Get the <code>Object</code> associated with the given key.  For
     * instance, If the <code>ServiceManager</code> had a
     * <code>LoggerComponent</code> stored and referenced by key,
     * the following could be used:
     * <pre>
     * try
     * {
     *     LoggerComponent log;
     *     myComponent = (LoggerComponent) manager.lookup( LoggerComponent.ROLE );
     * }
     * catch (...)
     * {
     *     ...
     * }
     * </pre>
     *
     * @param key The lookup key of the <code>Object</code> to retrieve.
     * @return an <code>Object</code> value
     * @throws ServiceException if an error occurs
     */
    Object lookup( String key )
        throws ServiceException;

    /**
     * Check to see if a <code>Object</code> exists for a key.
     *
     * @param key a string identifying the key to check.
     * @return True if the object exists, False if it does not.
     */
    boolean hasService( String key );

    /**
     * Return the <code>Object</code> when you are finished with it.  This
     * allows the <code>ServiceManager</code> to handle the End-Of-Life Lifecycle
     * events associated with the <code>Object</code>.  Please note, that no
     * Exception should be thrown at this point.  This is to allow easy use of the
     * ServiceManager system without having to trap Exceptions on a release.
     *
     * @param object The <code>Object</code> we are releasing.
     */
    void release( Object object );
}
