package org.codnect.firesnap.reflection.binder;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by Burak Koken on 9.9.2018.
 *
 * @author Burak Koken
 */
public class TypeFactory {

    /**
     * Create a new parameterized type.
     *
     * @param rawType raw type
     * @param actualTypeArgs actual type arguments
     * @param ownerType owner type
     * @return a new parameterized type
     */
    public static ParameterizedType createParameterizedType(final Type rawType,
                                                            final Type[] actualTypeArgs,
                                                            final Type ownerType) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return actualTypeArgs;
            }

            @Override
            public Type getRawType() {
                return rawType;
            }

            @Override
            public Type getOwnerType() {
                return ownerType;
            }

            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof ParameterizedType)) {
                    return false;
                }
                ParameterizedType other = (ParameterizedType)obj;
                return Arrays.equals(getActualTypeArguments(), other.getActualTypeArguments())
                        && compareObjects(getRawType(), other.getRawType())
                        && compareObjects(getOwnerType(), other.getOwnerType()
                );
            }

            @Override
            public int hashCode() {
                return computeHashCode(getActualTypeArguments())
                        ^ computeHashCode(getRawType())
                        ^ computeHashCode(getOwnerType());
            }
        };
    }

    /**
     * Create a new array type.
     *
     * @param type an instance of type
     * @return a new array type
     */
    public static Type createArrayType(Type type) {
        if (type instanceof Class) {
            return Array.newInstance((Class)type, 0).getClass();
        }
        return TypeFactory.createGenericArrayType(type);
    }

    /**
     * Create a new generic array type.
     *
     * @param type an instance of type
     * @return a new generic type
     */
    private static GenericArrayType createGenericArrayType(final Type type) {
        return new GenericArrayType() {
            @Override
            public Type getGenericComponentType() {
                return type;
            }

            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof GenericArrayType)) {
                    return false;
                }
                GenericArrayType other = (GenericArrayType)obj;
                return compareObjects(getGenericComponentType(), other.getGenericComponentType());
            }

            @Override
            public int hashCode() {
                return computeHashCode(getGenericComponentType());
            }
        };
    }

    /**
     * Compute the hash code for specified object.
     *
     * @return hash code value
     */
    private static int computeHashCode(Object object) {
        if (object == null) {
            return 0;
        }
        return object.hashCode();
    }

    /**
     * Compare two objects, if they are same, it returns true.
     * Otherwise, it returns false.
     *
     * @param o1 first object
     * @param o2 second object
     * @return if they are same, it returns true. Otherwise,
     * it returns false.
     */
    private static boolean compareObjects(Object o1, Object o2) {
        if(o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }

}
