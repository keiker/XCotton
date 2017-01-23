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
 * Basic enum class for type-safe enums with values. Valued enum items can be compared and ordered
 * with the provided methods. Should be used as an abstract base. For example:
 *
 * <pre>
 * import org.apache.avalon.framework.ValuedEnum;
 *
 * public final class JavaVersion
 *   extends ValuedEnum
 * {
 *   //standard enums for version of JVM
 *   public static final JavaVersion  JAVA1_0  = new JavaVersion( "Java 1.0", 100 );
 *   public static final JavaVersion  JAVA1_1  = new JavaVersion( "Java 1.1", 110 );
 *   public static final JavaVersion  JAVA1_2  = new JavaVersion( "Java 1.2", 120 );
 *   public static final JavaVersion  JAVA1_3  = new JavaVersion( "Java 1.3", 130 );
 *
 *   private JavaVersion( final String name, final int value )
 *   {
 *     super( name, value );
 *   }
 * }
 * </pre>
 *
 * The above class could then be used as follows:
 * <pre>
 * import org.apache.avalon.framework.context.Context;
 * import org.apache.avalon.framework.context.Contextualizable;
 * import org.apache.avalon.framework.context.ContextException;
 *
 * public class MyComponent implements Contextualizable
 * {
 *   JavaVersion requiredVer = JavaVersion.JAVA1_2;
 *
 *   public void contextualize(Context context)
 *       throws ContextException
 *   {
 *     JavaVersion ver = (JavaVersion)context.get("java.version");
 *     if ( ver.isLessThan( requiredVer ) )
 *     {
 *       throw new RuntimeException( requiredVer.getName()+" or higher required" );
 *     }
 *   }
 * }
 * </pre>
 *
 * As with <code>Enum</code>, the {@link #ValuedEnum(String, int, Map)} constructor can be used to
 * populate a <code>Map</code>, from which further functionality can be derived.
 *
 * <p>
 * <em>NOTE:</em> between 4.0 and 4.1, the constructors' access has been changed
 * from <code>public</code> to <code>protected</code>. This is to prevent users
 * of the Enum breaking type-safety by defining new Enum items. All Enum items
 * should be defined in the Enum class, as shown above.
 * </p>
 *
 * @author <a href="mailto:dev@avalon.apache.org">Avalon Development Team</a>
 * @version CVS $Revision: 1.19 $ $Date: 2003/02/11 15:58:37 $
 */
public abstract class ValuedEnum
    extends Enum
{
    /**
     * The value contained in enum.
     */
    private final int m_value;

    /**
     * Constructor for enum item.
     *
     * <p>
     * <em>Note:</em> access changed from <code>public</code> to
     * <code>protected</code> after 4.0. See class description.
     * </p>
     *
     *
     * @param name the name of enum item.
     * @param value the value of enum item.
     */
    protected ValuedEnum( final String name, final int value )
    {
        this( name, value, null );
    }

    /**
     * Constructor for enum item so that it gets added to Map at creation.
     * Adding to a map is useful for implementing find...() style methods.
     *
     * </p>
     * <em>Note:</em> access changed from <code>public</code> to
     * <code>protected</code> after 4.0. See class description.
     * </p>
     *
     * @param name the name of enum item.
     * @param value the value of enum item.
     * @param map the <code>Map</code> to add enum item to.
     */
    protected ValuedEnum( final String name, final int value, final Map map )
    {
        super( name, map );
        m_value = value;
    }

    /**
     * Get value of enum item.
     *
     * @return the enum item's value.
     */
    public final int getValue()
    {
        return m_value;
    }

    /**
     * Test if enum item is equal in value to other enum.
     *
     * @param other the other enum
     * @return true if equal
     */
    public final boolean isEqualTo( final ValuedEnum other )
    {
        return m_value == other.m_value;
    }

    /**
     * Test if enum item is greater than in value to other enum.
     *
     * @param other the other enum
     * @return true if greater than
     */
    public final boolean isGreaterThan( final ValuedEnum other )
    {
        return m_value > other.m_value;
    }

    /**
     * Test if enum item is greater than or equal in value to other enum.
     *
     * @param other the other enum
     * @return true if greater than or equal
     */
    public final boolean isGreaterThanOrEqual( final ValuedEnum other )
    {
        return m_value >= other.m_value;
    }

    /**
     * Test if enum item is less than in value to other enum.
     *
     * @param other the other enum
     * @return true if less than
     */
    public final boolean isLessThan( final ValuedEnum other )
    {
        return m_value < other.m_value;
    }

    /**
     * Test if enum item is less than or equal in value to other enum.
     *
     * @param other the other enum
     * @return true if less than or equal
     */
    public final boolean isLessThanOrEqual( final ValuedEnum other )
    {
        return m_value <= other.m_value;
    }

    /**
     * Override toString method to produce human readable description.
     *
     * @return String in the form <code>type[name=value]</code>, eg.:
     * <code>JavaVersion[Java 1.0=100]</code>.
     */
    public String toString()
    {
        return getClass().getName() + "[" + getName() + "=" + m_value + "]";
    }
}

