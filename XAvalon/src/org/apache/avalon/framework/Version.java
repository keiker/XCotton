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

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * This class is used to hold version information pertaining to a Component or interface.
 * <p />
 *
 * The version number of a <code>Component</code> is made up of three
 * dot-separated fields:
 * <p />
 * &quot;<b>major.minor.micro</b>&quot;
 * <p />
 * The <b>major</b>, <b>minor</b> and <b>micro</b> fields are
 * <i>integer</i> numbers represented in decimal notation and have the
 * following meaning:
 * <ul>
 *
 * <p /><li><b>major</b> - When the major version changes (in ex. from
 * &quot;1.5.12&quot; to &quot;2.0.0&quot;), then backward compatibility
 * with previous releases is not granted.</li><p />
 *
 * <p /><li><b>minor</b> - When the minor version changes (in ex. from
 * &quot;1.5.12&quot; to &quot;1.6.0&quot;), then backward compatibility
 * with previous releases is granted, but something changed in the
 * implementation of the Component. (ie it methods could have been added)</li><p />
 *
 * <p /><li><b>micro</b> - When the micro version changes (in ex.
 * from &quot;1.5.12&quot; to &quot;1.5.13&quot;), then the the changes are
 * small forward compatible bug fixes or documentation modifications etc.
 * </li>
 * </ul>
 *
 * @author <a href="mailto:dev@avalon.apache.org">Avalon Development Team</a>
 * @version CVS $Revision: 1.30 $ $Date: 2003/07/12 16:49:55 $
 */
public final class Version
    implements Comparable, Serializable
{
    private int m_major;
    private int m_minor;
    private int m_micro;

    /**
     * Parse a version out of a string.
     * The version string format is <major>.<minor>.<micro> where
     * both minor and micro are optional.
     *
     * @param version The input version string
     * @return the new Version object
     * @throws NumberFormatException if an error occurs
     * @throws IllegalArgumentException if an error occurs
     * @since 4.1
     */
    public static Version getVersion( final String version )
        throws NumberFormatException, IllegalArgumentException
    {
        final StringTokenizer tokenizer = new StringTokenizer( version, "." );
        final String[] levels = new String[ tokenizer.countTokens() ];
        for( int i = 0; i < levels.length; i++ )
        {
            levels[ i ] = tokenizer.nextToken();
        }

        int major = -1;
        if( 0 < levels.length )
        {
            major = Integer.parseInt( levels[ 0 ] );
        }

        int minor = 0;
        if( 1 < levels.length )
        {
            minor = Integer.parseInt( levels[ 1 ] );
        }

        int micro = 0;
        if( 2 < levels.length )
        {
            micro = Integer.parseInt( levels[ 2 ] );
        }

        return new Version( major, minor, micro );
    }

    /**
     * Create a new instance of a <code>Version</code> object with the
     * specified version numbers.
     *
     * @param major This <code>Version</code> major number.
     * @param minor This <code>Version</code> minor number.
     * @param micro This <code>Version</code> micro number.
     */
    public Version( final int major, final int minor, final int micro )
    {
        m_major = major;
        m_minor = minor;
        m_micro = micro;
    }

    /**
     * Retrieve major component of version.
     *
     * @return the major component of version
     * @since 4.1
     */
    public int getMajor()
    {
        return m_major;
    }

    /**
     * Retrieve minor component of version.
     *
     * @return the minor component of version
     * @since 4.1
     */
    public int getMinor()
    {
        return m_minor;
    }

    /**
     * Retrieve micro component of version.
     *
     * @return the micro component of version.
     * @since 4.1
     */
    public int getMicro()
    {
        return m_micro;
    }

    /**
     * Check this <code>Version</code> against another for equality.
     * <p />
     * If this <code>Version</code> is compatible with the specified one, then
     * <b>true</b> is returned, otherwise <b>false</b>.
     *
     * @param other The other <code>Version</code> object to be compared with this
     *          for equality.
     * @return <b>true</b> if this <code>Version</code> is compatible with the specified one
     * @since 4.1
     */
    public boolean equals( final Version other )
    {
        boolean isEqual = ( getMajor() == other.getMajor() );
        
        if ( isEqual )
        {
            isEqual = ( getMinor() == other.getMinor() );
        }
        
        if ( isEqual )
        {
            isEqual = ( getMicro() == other.getMicro() );
        }

        return isEqual;
    }

    /**
     * Indicates whether some other object is "equal to" this <code>Version</code>.
     * Returns <b>true</b> if the other object is an instance of <code>Version</code>
     * and has the same major, minor, and micro components.
     *
     * @param other an <code>Object</code> value
     * @return <b>true</b> if the other object is equal to this <code>Version</code>
     */
    public boolean equals( final Object other )
    {
        boolean isEqual = false;
        
        if( other instanceof Version )
        {
            isEqual = equals( (Version)other );
        }

        return isEqual;
    }
    
    /**
     * Add a hashing function to ensure the Version object is
     * treated as expected in hashmaps and sets.  NOTE: any
     * time the equals() is overridden, hashCode() should also
     * be overridden.
     * 
     * @return the hashCode
     */
    public int hashCode()
    {
        int hash = getMajor();
        hash >>>= 17;
        hash += getMinor();
        hash >>>= 17;
        hash += getMicro();
        
        return hash;
    }

    /**
     * Check this <code>Version</code> against another for compliancy
     * (compatibility).
     * <p />
     * If this <code>Version</code> is compatible with the specified one, then
     * <b>true</b> is returned, otherwise <b>false</b>. Be careful when using
     * this method since, in example, version 1.3.7 is compliant to version
     * 1.3.6, while the opposite is not.
     * <p />
     * The following example displays the expected behaviour and results of version.
     * <pre>
     * final Version v1 = new Version( 1, 3 , 6 );
     * final Version v2 = new Version( 1, 3 , 7 );
     * final Version v3 = new Version( 1, 4 , 0 );
     * final Version v4 = new Version( 2, 0 , 1 );
     *
     * assert(   v1.complies( v1 ) );
     * assert( ! v1.complies( v2 ) );
     * assert(   v2.complies( v1 ) );
     * assert( ! v1.complies( v3 ) );
     * assert(   v3.complies( v1 ) );
     * assert( ! v1.complies( v4 ) );
     * assert( ! v4.complies( v1 ) );
     * </pre>
     *
     * @param other The other <code>Version</code> object to be compared with this
     *              for compliancy (compatibility).
     * @return <b>true</b> if this <code>Version</code> is compatible with the specified one
     */
    public boolean complies( final Version other )
    {
        if( other.m_major == -1 )
        {
            return true;
        }
        if( m_major != other.m_major )
        {
            return false;
        }
        else if( m_minor < other.m_minor )
        {
            //If of major version but lower minor version then incompatible
            return false;
        }
        else if( m_minor == other.m_minor 
            && m_micro < other.m_micro )
        {
            //If same major version, same minor version but lower micro level
            //then incompatible
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Overload toString to report version correctly.
     *
     * @return the dot seperated version string
     */
    public String toString()
    {
        return m_major + "." + m_minor + "." + m_micro;
    }

    /**
     * Compare two versions together according to the
     * Comparable interface.
     * 
     * @return number indicating relative value (-1, 0, 1)
     */
    public int compareTo(Object o) {
        Version other = (Version)o;
        int val = 0;

        if ( getMajor() < other.getMajor() ) val = -1;
        if ( 0 == val && getMajor() > other.getMajor() ) val = 1;

        if ( 0 == val && getMinor() < other.getMinor() ) val = -1;
        if ( 0 == val && getMinor() > other.getMinor() ) val = 1;

        if ( 0 == val && getMicro() < other.getMicro() ) val = -1;
        if ( 0 == val && getMicro() > other.getMicro() ) val = 1;

        return val;
    }
}
