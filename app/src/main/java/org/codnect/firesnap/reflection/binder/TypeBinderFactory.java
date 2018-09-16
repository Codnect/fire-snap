package org.codnect.firesnap.reflection.binder;

import org.codnect.firesnap.exception.FireSnapException;
import org.codnect.firesnap.exception.NotResolvedException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Created by Burak Koken on 8.9.2018.
 *
 * @author Burak Koken
 */
public class TypeBinderFactory {

    public TypeBinderFactory() {

    }

    /**
     * Get the type binder for specified type.
     *
     * @param type an instance of the type
     * @return type binder
     */
    public TypeBinder getBinder(Type type) {
        if(type == null) {
            return IdentityTypeBinder.getInstance();
        }
        return createBinder(type);
    }

    /**
     * Get the compound type binder according to specified type
     * and type binder.
     *
     * @param type an instance of the type
     * @param typeBinder an instance of the type binder
     * @return compound type binder
     */
    public TypeBinder getBinder(Type type, TypeBinder typeBinder) {
        return CompoundTypeBinder.create(getBinder(type), typeBinder);
    }

    /**
     * Create a new type binder for specified type.
     *
     * @param type an instance of the type
     * @return type binder
     */
    private TypeBinder createBinder(Type type) {
        if(type instanceof Class) {
            Class classType = (Class) type;
            return CompoundTypeBinder.create(createSuperTypeBinder(classType),
                    getBinder(classType.getSuperclass()));
        } else if(type instanceof ParameterizedType) {
            return createBinder((ParameterizedType)type);
        }
        throw new IllegalArgumentException( "Invalid type for generating binder : " + type);
    }

    /**
     * Create a new type binder for specified parameterized type.
     *
     * @param type an instance of the parameterized type
     * @return type binder
     */
    private TypeBinder createBinder(ParameterizedType type) {
        Type[] actualTypeArguments = type.getActualTypeArguments();
        Type rawType = type.getRawType();
        if(rawType instanceof Class) {
            TypeVariable[] typeParameters = ((Class)rawType).getTypeParameters();
            return new SimpleTypeBinder(typeParameters, actualTypeArguments);
        }
        return IdentityTypeBinder.getInstance();
    }

    /**
     * Create a new type binder for specified class's super class.
     *
     * @param xClass a class
     * @return type binder
     */
    private TypeBinder createSuperTypeBinder(Class xClass) {
        Class superClass = xClass.getSuperclass();
        if(superClass == null) {
            return IdentityTypeBinder.getInstance();
        }

        Type[] formalArgs = superClass.getTypeParameters();
        Type genericSuperclass = xClass.getGenericSuperclass();
        if(genericSuperclass instanceof Class) {
            return IdentityTypeBinder.getInstance();
        } else if(genericSuperclass instanceof ParameterizedType) {
            Type[] actualArgs = ((ParameterizedType)genericSuperclass).getActualTypeArguments();
            return new SimpleTypeBinder(formalArgs, actualArgs);
        }

        throw new FireSnapException("Unreachable line!");
    }

    /**
     * Converts the specified type binder to an approximate type
     * binder.
     *
     * @param typeBinder an instance of the type binder
     * @return an approximate type binder
     */
    public TypeBinder toApproximateBinder(TypeBinder typeBinder) {
        return CompoundTypeBinder.create(new ApproximateTypeBinder(), typeBinder);
    }

}
