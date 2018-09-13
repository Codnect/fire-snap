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
     *
     * @return
     */
    @Override
    public Class getCollectionClass() {
        return ReflectionUtil.getCollectionClass(getApproximatedType());
    }

    /**
     *
     * @return
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
     *
     * @return
     */
    @Override
    public XClass getClassOrElementClass() {
        return toXClass(getApproximatedType());
    }

}
