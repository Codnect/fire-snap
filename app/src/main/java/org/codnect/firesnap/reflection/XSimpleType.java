package org.codnect.firesnap.reflection;

import org.codnect.firesnap.reflection.binder.TypeBinder;

import java.lang.reflect.Type;

/**
 * Created by Burak Koken on 3.6.2018.
 *
 * @author Burak Koken
 */
public class XSimpleType extends XType{

    protected XSimpleType(Type type, TypeBinder typeBinder, ReflectionManager reflectionManager) {
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
        return false;
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
        return toXClass(getApproximatedType());
    }

    /**
     *
     * @return
     */
    @Override
    public XClass getElementClass() {
        return toXClass(getApproximatedType());
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
