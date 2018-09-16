package org.codnect.firesnap.reflection;

import org.codnect.firesnap.reflection.binder.TypeBinder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.SortedMap;

/**
 * Created by Burak Koken on 3.6.2018.
 *
 * @author Burak Koken
 */
public class XCollectionType extends XType{

    protected XCollectionType(Type type, TypeBinder typeBinder, ReflectionManager reflectionManager) {
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
        return true;
    }

    /**
     * Get the type for this element.
     *
     * @return the type for this element.
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
        Type approximatedType = getApproximatedType();
        if(approximatedType instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) approximatedType).getActualTypeArguments();
            Type type;
            Class collectionClass = getCollectionClass();
            if(Map.class.isAssignableFrom(collectionClass) || SortedMap.class.isAssignableFrom(collectionClass)) {
                type = actualTypeArguments[1];
            } else {
                type = actualTypeArguments[0];
            }
            return toXClass(type);
        }
        return null;
    }

    /**
     * Get this element's collection class if element type is a
     * collection. Otherwise returns null value.
     *
     * @return this element's collection class
     */
    @Override
    public Class getCollectionClass() {
        return ReflectionUtil.getCollectionClass(getApproximatedType());
    }

    /**
     * Get the instance XClass of the map key type if this
     * element type is a map. Otherwise returns null value.
     *
     * @return the instance XClass of map key
     */
    @Override
    public XClass getMapKey() {
        Type approximatedType = getApproximatedType();
        if(approximatedType instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) approximatedType).getActualTypeArguments();
            Class collectionClass = getCollectionClass();
            if(Map.class.isAssignableFrom(collectionClass) || SortedMap.class.isAssignableFrom(collectionClass)) {
                toXClass(actualTypeArguments[0]);
            }

        }
        return null;
    }

    /**
     * Get the class or element class for this element.
     *
     * @return  the class or element class
     */
    @Override
    public XClass getClassOrElementClass() {
        return toXClass(getApproximatedType());
    }

}
