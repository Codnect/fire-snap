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
     * Get the type for this element.
     *
     * @return the type for this element
     */
    @Override
    public XClass getType() {
        return toXClass(getApproximatedType());
    }

    /**
     * Get the instance XClass of the element class for this type.
     * The type of this property's elements for arrays and maps,
     * the type of the property itself for everything else.
     *
     * @return the instance XClass of the element class
     */
    @Override
    public XClass getElementClass() {
        return toXClass(getApproximatedType());
    }

    /**
     * Get this element's collection class if element type is a
     * collection. Otherwise returns null value.
     *
     * @return this element's collection class
     */
    @Override
    public Class getCollectionClass() {
        return null;
    }

    /**
     * Get the instance XClass of the map key type if this
     * element type is a map. Otherwise returns null value.
     *
     * @return the instance XClass of map key
     */
    @Override
    public XClass getMapKey() {
        return null;
    }

    /**
     * Get the class or element class for this element.
     *
     * @return  the class or element class
     */
    @Override
    public XClass getClassOrElementClass() {
        return getElementClass();
    }

}
