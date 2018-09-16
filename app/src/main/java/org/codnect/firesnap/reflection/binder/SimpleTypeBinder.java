package org.codnect.firesnap.reflection.binder;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * SimpleTypeBinder class binds formal type arguments (typically T, E, etc.)
 * to actual types.
 *
 * Created by Burak Koken on 8.9.2018.
 *
 * @author Burak Koken
 */
public class SimpleTypeBinder implements TypeBinder{

    private Map<Type, Type> subsituteTypeMap = new HashMap<>();

    private SimpleTypeBinder() {

    }

    /**
     * Create a new simple type binder according to the specified formal and
     * actual type arguments.
     *
     * @param formalTypeArgs formal type arguments
     * @param actualTypeArgs actual type arguments.
     */
    public SimpleTypeBinder(Type[] formalTypeArgs, Type[] actualTypeArgs) {
        for (int index = 0;index < formalTypeArgs.length;index++) {
            subsituteTypeMap.put(formalTypeArgs[index], actualTypeArgs[index]);
        }
    }

    /**
     * Bind the specified type.
     *
     * @param type an instance of type
     * @return a new type for specified type.
     */
    @Override
    public Type bind(Type type) {
        if(type instanceof Class) {
            return type;
        } else if(type instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) type;
            Type originalComponentType = genericArrayType.getGenericComponentType();
            Type boundComponentType = bind(originalComponentType);
            if (originalComponentType == boundComponentType) {
                return genericArrayType;
            }
            return TypeFactory.createArrayType(boundComponentType);
        } else if(type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type[] originalArguments = parameterizedType.getActualTypeArguments();
            Type[] boundArguments = substitute(originalArguments);
            if (Arrays.equals(originalArguments, boundArguments)) {
                return parameterizedType;
            }
            return TypeFactory.createParameterizedType(parameterizedType.getRawType(),
                    boundArguments,
                    parameterizedType.getOwnerType());
        } else if(type instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable)type;
            if (!subsituteTypeMap.containsKey(typeVariable)) {
                return typeVariable;
            }
            return subsituteTypeMap.get(typeVariable);
        } else if(type instanceof WildcardType) {
            return type;
        }
        return null;
    }

    /**
     * Substitute all types.
     *
     * @param types instances of the types
     * @return new types
     */
    private Type[] substitute(Type[] types) {
        Type[] substTypes = new Type[types.length];
        for (int index = 0;index < substTypes.length ;index++) {
            substTypes[index] = bind(types[index]);
        }
        return substTypes;
    }

}
