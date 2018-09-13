package org.codnect.firesnap.reflection;

import org.codnect.firesnap.reflection.binder.TypeBinder;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/**
 * Created by Burak Koken on 3.6.2018.
 *
 * @author Burak Koken
 */
public class XArrayType extends XType {

    protected XArrayType(Type type, TypeBinder typeBinder, ReflectionManager reflectionManager) {
        super(type, typeBinder, reflectionManager);
    }

    /**
     * Determines if this type is an array.
     *
     * @return if this type is an array, it returns
     * true. Otherwise, it returns false.
     */
    @Override
    public boolean isArray() {
        return true;
    }

    /**
     * Determines if this type is a collection.
     *
     * @return if this type is a collection, it returns
     * true. Otherwise, it returns false.
     */
    @Override
    public boolean isCollection() {
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public XClass getType() {
        Type approximatedType = getApproximatedType();
        Type componentType = null;
        if(approximatedType instanceof Class) {
            componentType = ((Class) approximatedType).getComponentType();
        } else if(approximatedType instanceof GenericArrayType) {
            componentType = ((GenericArrayType) approximatedType).getGenericComponentType();
        }
        else {
            throw new IllegalArgumentException(approximatedType + " is not an array type" );
        }
        Type boundType = null;
        if (componentType instanceof Class) {
            boundType = Array.newInstance((Class) componentType, 0).getClass();
        }
        return toXClass(boundType);
    }

    /**
     *
     * @return
     */
    @Override
    public XClass getElementClass() {
        Type approximatedType = getApproximatedType();
        Type componentType;
        if(approximatedType instanceof Class) {
            componentType = ((Class) approximatedType).getComponentType();
        } else if(approximatedType instanceof GenericArrayType) {
            componentType = ((GenericArrayType) approximatedType).getGenericComponentType();
        } else {
            throw new IllegalArgumentException(approximatedType + " is not an array type" );
        }
        return toXClass(componentType);
    }

    /**
     *
     * @return
     */
    @Override
    public Class getCollectionClass() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public XClass getMapKey() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public XClass getClassOrElementClass() {
        return getElementClass();
    }

}
