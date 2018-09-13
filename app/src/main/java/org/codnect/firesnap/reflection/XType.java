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
     *
     * @return
     */
    public abstract XClass getType();

    /**
     *
     * @return
     */
    public abstract XClass getElementClass();

    /**
     *
     */
    public abstract Class getCollectionClass();

    /**
     *
     */
    public abstract XClass getMapKey();

    /**
     *
     * @return
     */
    public abstract XClass getClassOrElementClass();

    /**
     *
     * @return
     */
    public boolean isResolved() {
        return ReflectionUtil.isResolved(boundType);
    }

    /**
     *
     *
     * @return
     */
    protected Type getApproximatedType() {
        return approximatedType;
    }

    /**
     *
     * @param type
     * @return
     */
    protected XClass toXClass(Type type) {
        return reflectionManager.getXClass(type, typeBinder);
    }

}
