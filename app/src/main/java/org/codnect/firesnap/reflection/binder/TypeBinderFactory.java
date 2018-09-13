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
     *
     * @param type
     * @return
     */
    public TypeBinder getBinder(Type type) {
        if(type == null) {
            return IdentityTypeBinder.getInstance();
        }
        return createBinder(type);
    }

    /**
     *
     * @param type
     * @param typeBinder
     * @return
     */
    public TypeBinder getBinder(Type type, TypeBinder typeBinder) {
        return CompoundTypeBinder.create(getBinder(type), typeBinder);
    }

    /**
     *
     * @param type
     * @return
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
     *
     * @param type
     * @return
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
     *
     * @param xClass
     * @return
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
     *
     * @param typeBinder
     * @return
     */
    public TypeBinder toApproximateBinder(TypeBinder typeBinder) {
        return CompoundTypeBinder.create(new ApproximateTypeBinder(), typeBinder);
    }

}
