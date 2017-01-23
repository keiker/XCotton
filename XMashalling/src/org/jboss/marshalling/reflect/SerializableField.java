/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.marshalling.reflect;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Field;
import org.jboss.marshalling.util.Kind;

/**
 * Reflection information about a field on a serializable class.
 */
public final class SerializableField {
    // the type of the field itself
    private final Class<?> type;
    private final Field field;
    private final String name;
    private final boolean unshared;
    private final Kind kind;

    public SerializableField(Class<?> type, String name, boolean unshared) {
        this(type, name, unshared, null);
    }

    SerializableField(Class<?> type, String name, boolean unshared, final Field field) {
        assert field == null || field.isAccessible();
        this.type = type;
        this.name = name;
        this.unshared = unshared;
        this.field = field;
        if (field != null) {
            // verify field information
            if (field.getType() != type) {
                throw new IllegalStateException("Constructed a serializable field with the wrong type (field type is " + field.getType() + ", our type is " + type + ")");
            }
            if (! field.getName().equals(name)) {
                throw new IllegalStateException("Constructed a serializable field with the wrong name (field name is " + field.getName() + ", our name is " + name + ")");
            }
        }
        // todo - see if a small Map is faster
        if (type == boolean.class) {
            kind = Kind.BOOLEAN;
        } else if (type == byte.class) {
            kind = Kind.BYTE;
        } else if (type == short.class) {
            kind = Kind.SHORT;
        } else if (type == int.class) {
            kind = Kind.INT;
        } else if (type == long.class) {
            kind = Kind.LONG;
        } else if (type == char.class) {
            kind = Kind.CHAR;
        } else if (type == float.class) {
            kind = Kind.FLOAT;
        } else if (type == double.class) {
            kind = Kind.DOUBLE;
        } else {
            kind = Kind.OBJECT;
        }
    }

    /**
     * Get the reflection {@code Field} for this serializable field.  The resultant field will be accessible.
     *
     * @return the reflection field
     */
    public Field getField() {
        return field;
    }

    /**
     * Get the name of the field.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Determine whether this field is marked as "unshared".
     *
     * @return {@code true} if the field is unshared
     */
    public boolean isUnshared() {
        return unshared;
    }

    /**
     * Get the kind of field.
     *
     * @return the kind
     */
    public Kind getKind() {
        return kind;
    }

    /**
     * Get the field type.
     *
     * @return the field type
     */
    public Class<?> getType() throws ClassNotFoundException {
        return type;
    }

    /**
     * Read the field value from the stream.
     *
     * @param instance the instance
     * @param input the source stream
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if a class could not be loaded
     */
    public void readFrom(Object instance, ObjectInput input) throws IOException, ClassNotFoundException {
        try {
            switch (kind) {
                case BOOLEAN:
                    field.setBoolean(instance, input.readBoolean());
                    break;
                case BYTE:
                    field.setByte(instance, input.readByte());
                    break;
                case CHAR:
                    field.setChar(instance, input.readChar());
                    break;
                case DOUBLE:
                    field.setDouble(instance, input.readDouble());
                    break;
                case FLOAT:
                    field.setFloat(instance, input.readFloat());
                    break;
                case INT:
                    field.setInt(instance, input.readInt());
                    break;
                case LONG:
                    field.setLong(instance, input.readLong());
                    break;
                case SHORT:
                    field.setShort(instance, input.readShort());
                    break;
                case OBJECT:
                    field.set(instance, input.readObject());
                    break;
                default:
                    throw new IllegalStateException();
            }
        } catch (IllegalAccessException e) {
            // should not be possible
            IllegalAccessError error = new IllegalAccessError(e.getMessage());
            error.setStackTrace(e.getStackTrace());
            throw error;
        }
    }

    public void writeTo(Object instance, ObjectOutput output) throws IOException {
        try {
            switch (kind) {
                case BOOLEAN:
                    output.writeBoolean(field.getBoolean(instance));
                    break;
                case BYTE:
                    output.writeByte(field.getByte(instance));
                    break;
                case CHAR:
                    output.writeChar(field.getChar(instance));
                    break;
                case DOUBLE:
                    output.writeDouble(field.getDouble(instance));
                    break;
                case FLOAT:
                    output.writeFloat(field.getFloat(instance));
                    break;
                case INT:
                    output.writeInt(field.getInt(instance));
                    break;
                case LONG:
                    output.writeLong(field.getLong(instance));
                    break;
                case SHORT:
                    output.writeShort(field.getShort(instance));
                    break;
                case OBJECT:
                    output.writeObject(field.get(instance));
                    break;
                default:
                    throw new IllegalStateException();
            }
        } catch (IllegalAccessException e) {
            // should not be possible
            IllegalAccessError error = new IllegalAccessError(e.getMessage());
            error.setStackTrace(e.getStackTrace());
            throw error;
        }
    }
}
