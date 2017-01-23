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
package org.apache.avalon.framework;

import java.util.Map;

/**
 * Basic enum class for type-safe enums. Should be used as an abstract base. For example:
 *
 * <pre>
 * import org.apache.avalon.framework.Enum;
 *
 * public final class Color extends Enum {
 *   public static final Color RED = new Color( "Red" );
 *   public static final Color GREEN = new Color( "Green" );
 *   public static final Color BLUE = new Color( "Blue" );
 *
 *   private Color( final String color )
 *   {
 *     super( color );
 *   }
 * }
 * </pre>
 *
 * If further operations, such as iterating over all items, are required, the
 * {@link #Enum(String, Map)} constructor can be used to populate a <code>Map</code>, from which
 * further functionality can be derived:
 * <pre>
 * public final class Color extends Enum {
 *   static final Map map = new HashMap();
 *
 *   public static final Color RED = new Color( "Red", map );
 *   public static final Color GREEN = new Color( "Green", map );
 *   public static final Color BLUE = new Color( "Blue", map );
 *
 *   private Color( final String color, final Map map )
 *   {
 *     super( color, map );
 *   }
 *
 *   public static Iterator iterator()
 *   {
 *     return map.values().iterator();
 *   }
 * }
 * </pre>
 *
 * <p>
 * <em>NOTE:</em> between 4.0 and 4.1, the constructors' access has been changed
 * from <code>public</code> to <code>protected</code>. This is to prevent users
 * of the Enum breaking type-safety by defining new Enum items. All Enum items
 * should be defined in the Enum class, as shown above.
 * </p>
 *
 *
 * @author <a href="mailto:dev@avalon.apache.org">Avalon Development Team</a>
 * @version CVS $Revision: 1.24 $ $Date: 2003/04/07 08:31:15 $
 */
public abstract class Enum
{
    /**
     * The string representation of the Enum.
     */
    private final String m_name;

    /**
     * Constructor to add a new named item.
     * <p>
     * <em>Note:</em> access changed from <code>public</code> to
     * <code>protected</code> after 4.0. See class description.
     * </p>
     *
     * @param name Name of the item.
     */
    protected Enum( final String name )
    {
        this( name, null );
    }

    /**
     * Constructor to add a new named item.
     * <p>
     * <em>Note:</em> access changed from <code>public</code> to
     * <code>protected</code> after 4.0. See class description.
     * </p>
     *
     * @param name Name of the item.
     * @param map A <code>Map</code>, to which will be added a pointer to the newly constructed
     * object.
     */
    protected Enum( final String name, final Map map )
    {
        m_name = name;
        if( null != map )
        {
            map.put( name, this );
        }
    }

    /**
     * Tests for equality. Two Enum:s are considered equal
     * if they have the same class names and the same names.
     * Identity is tested for first, so this method runs fast.
     * The method is also declared final - I (LSutic) did this to
     * allow the JIT to inline it easily.
     *
     * @param other the other object
     * @return the equality status
     */
    public final boolean equals( final Object other )
    {
        if( null == other )
        {
            return false;
        }
        else
        {
            return other == this
                || ( other.getClass().getName().equals( this.getClass().getName() )
                && m_name.equals( ( (Enum)other ).m_name ) );
        }
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    public int hashCode()
    {
        return m_name.hashCode();
    }

    /**
     * Retrieve the name of this Enum item, set in the constructor.
     * @return the name <code>String</code> of this Enum item
     */
    public final String getName()
    {
        return m_name;
    }

    /**
     * Human readable description of this Enum item. For use when debugging.
     * @return String in the form <code>type[name]</code>, eg.:
     * <code>Color[Red]</code>.
     */
    public String toString()
    {
        return getClass().getName() + "[" + m_name + "]";
    }
}
