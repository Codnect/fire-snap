package org.codnect.firesnap.reflection;

import org.codnect.firesnap.reflection.binder.TypeBinder;

import java.lang.reflect.Type;

/**
 * Created by Burak Koken on 24.5.2018.
 *
 * @author Burak Koken
 */
public abstract class XType {

    private Type boundType;
    private Type approximatedType;
    private TypeBinder typeBinder;
    private ReflectionManager reflectionManager;

    protected XType(Type type, TypeBinder typeBinder, ReflectionManager reflectionManager) {
        this.boundType = typeBinder.bind(type);
        this.approximatedType = reflectionManager.toApproximateBinder(typeBinder).bind(type);
        this.typeBinder = typeBinder;
        this.reflectionManager = reflectionManager;
    }

    /**
     * Determines if this type is an array.
     *
     * @return if this type is an array, it returns
     * true. Otherwise, it returns false.
     */
    public abstract boolean isArray();

    /**
     * Determines if this type is a collection.
     *
     * @return if this type is a collection, it returns
     * true. Otherwise, it returns false.
     */
    public abstract boolean isCollection();

    /**
     * Get the type for this element.
     *
     * @return the type for this element.
     */
    public abstract XClass getType();

    /**
     * Get the instance XClass of the element class for this type.
     * The type of this property's elements for arrays and maps,
     * the type of the property itself for everything else.
     *
     * @return the instance XClass of the element class
     */
    public abstract XClass getElementClass();

    /**
     * Get this element's collection class if element type is a
     * collection. Otherwise returns null value.
     *
     * @return this element's collection class
     */
    public abstract Class getCollectionClass();

    /**
     * Get the instance XClass of the map key type if this
     * element type is a map. Otherwise returns null value.
     *
     * @return the instance XClass of map key
     */
    public abstract XClass getMapKey();

    /**
     * Get the class or element class for this element.
     *
     * @return  the class or element class
     */
    public abstract XClass getClassOrElementClass();


    /**
     * Determines if this element is a resolved type.
     *
     * @return if this element is a resolved type, it
     * returns true. Otherwise, it returns false.
     */
    public boolean isResolved() {
        return ReflectionUtil.isResolved(boundType);
    }

    /**
     * Get the approximated type for this element.
     *
     * @return the approximated type for this element
     */
    protected Type getApproximatedType() {
        return approximatedType;
    }

    /**
     * Converts the specified type to an instance of the XClass.
     *
     * @param type a type that will be converted to an instance
     *        of XClass.
     * @return the instance of the XClass for specified type
     */
    protected XClass toXClass(Type type) {
        return reflectionManager.getXClass(type, typeBinder);
    }

}
