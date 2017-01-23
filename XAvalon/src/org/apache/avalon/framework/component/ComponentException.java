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

import org.apache.avalon.framework.CascadingException;

/**
 * The exception thrown to indicate a problem with Components.
 * It is usually thrown by ComponentManager or ComponentSelector.
 *
 * <p>
 *  <span style="color: red">Deprecated: </span><i>
 *    Use {@link org.apache.avalon.framework.service.ServiceException} instead.
 *  </i>
 * </p>
 *
 * @author <a href="mailto:dev@avalon.apache.org">Avalon Development Team</a>
 * @version CVS $Revision: 1.20 $ $Date: 2003/02/11 15:58:38 $
 */
public class ComponentException
    extends CascadingException
{
    private final String m_key;

    /**
     * Construct a new <code>ComponentException</code> instance.
     * @param key the lookup key
     * @param message the exception message
     * @param throwable the throwable
     */
    public ComponentException( final String key,
                               final String message,
                               final Throwable throwable )
    {
        super( message, throwable );
        m_key = key;
    }

    /**
     * Construct a new <code>ComponentException</code> instance.
     *
     * @deprecated use the String, String, Throwable version to record the role
     * @param message the exception message
     * @param throwable the throwable
     */
    public ComponentException( final String message, final Throwable throwable )
    {
        this( null, message, throwable );
    }

    /**
     * Construct a new <code>ComponentException</code> instance.
     *
     * @deprecated use the String, String version to record the role
     * @param message the exception message
     */
    public ComponentException( final String message )
    {
        this( null, message, null );
    }

    /**
     * Construct a new <code>ComponentException</code> instance.
     * @param key the lookup key
     * @param message the exception message
     */
    public ComponentException( final String key, final String message )
    {
        this( key, message, null );
    }

    /**
     * Get the key which let to the exception.  May be null.
     *
     * @return The key which let to the exception.
     */
    public final String getKey()
    {
        return m_key;
    }

    /**
     * Get the key which let to the exception.  May be null.
     *
     * @return The key which let to the exception.
     * @deprecated Use getKey instead
     */
    public final String getRole()
    {
        return getKey();
    }

    /**
     * Return a message describing the exception.
     *
     * @return exception message.
     */
    public String getMessage()
    {
        if( m_key == null )
        {
            return super.getMessage();
        }
        else
        {
            return super.getMessage() + " (key [" + m_key + "])";
        }
    }
}
