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
     *
     * @param rawType
     * @param actualTypeArgs
     * @param ownerType
     * @return
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
     *
     * @param type
     * @return
     */
    public static Type createArrayType(Type type) {
        if (type instanceof Class) {
            return Array.newInstance((Class)type, 0).getClass();
        }
        return TypeFactory.createGenericArrayType(type);
    }

    /**
     *
     * @param type
     * @return
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
     *
     * @param object
     * @return
     */
    private static int computeHashCode(Object object) {
        if (object == null) {
            return 0;
        }
        return object.hashCode();
    }

    /**
     *
     * @param o1
     * @param o2
     * @return
     */
    private static boolean compareObjects(Object o1, Object o2) {
        if(o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }

}
