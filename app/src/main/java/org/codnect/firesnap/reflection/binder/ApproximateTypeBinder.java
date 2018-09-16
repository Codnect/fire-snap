package org.codnect.firesnap.reflection.binder;

import org.codnect.firesnap.exception.NotResolvedException;
import org.codnect.firesnap.reflection.ReflectionUtil;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/**
 * ApproximateTypeBinder approximates the unresolved generic simple
 * type or collection to their nearest upper binding.
 *
 * @author Burak Koken
 */
public class ApproximateTypeBinder implements TypeBinder {

    /**
     * Bind the specified type.
     *
     * @param type an instance of type
     * @return a new type for specified type.
     */
    @Override
    public Type bind(Type type) {
        Type result;
        if(type instanceof Class) {
            return type;
        } else if(type instanceof GenericArrayType) {
            if(ReflectionUtil.isResolved(type)) {
                return type;
            } else {
                Type componentType = ((GenericArrayType)type).getGenericComponentType();
                Type boundComponentType = bind(componentType);
                if(boundComponentType instanceof Class) {
                    result = Array.newInstance((Class)boundComponentType, 0).getClass();
                } else {
                    return Object[].class;
                }
            }
        } else if(type instanceof ParameterizedType) {
            if (ReflectionUtil.isResolved(type)) {
                return type;
            } else if (!ReflectionUtil.isCollection(type)) {
                return Object.class;
            } else {
                ParameterizedType parameterizedType = (ParameterizedType)type;
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                Type[] approximatedTypeArguments = new Type[typeArguments.length];
                for (int index = 0;index < typeArguments.length;index++) {
                    approximatedTypeArguments[index] = coarseApproximation(typeArguments[index]);
                }

                result = TypeFactory.createParameterizedType(
                        bind(parameterizedType.getRawType()),
                        approximatedTypeArguments,
                        parameterizedType.getOwnerType()
                );
            }
        } else if(type instanceof WildcardType) {
            result = type;
        } else {
            result = coarseApproximation(type);
        }

        if(!ReflectionUtil.isResolved(result)) {
            throw new NotResolvedException("Type is not resolved : " + type.toString());
        }

        return result;
    }

    /**
     * Apply coarse approximation for specified type.
     *
     * @param type an instance of the type
     * @return new type that was applied coarse approximation.
     */
    private Type coarseApproximation(Type type) {
        if(type instanceof ParameterizedType) {
            if(ReflectionUtil.isResolved(type)) {
                return type;
            }
            else {
                return Object.class;
            }
        } else if(type instanceof GenericArrayType) {
            if (ReflectionUtil.isResolved(type)) {
                return type;
            }
            return Object[].class;
        } else if(type instanceof TypeVariable) {
            return approximateTo(((TypeVariable)type).getBounds());
        } else if(type instanceof WildcardType) {
            return approximateTo(((WildcardType)type).getUpperBounds());
        }
        return type;
    }

    /**
     * Get approximate type for specified bounds.
     *
     * @param bounds type bounds
     * @return new type
     */
    private Type approximateTo(Type[] bounds) {
        if (bounds.length != 1) {
            return Object.class;
        }
        return coarseApproximation(bounds[0]);
    }

}
