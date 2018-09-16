package org.codnect.firesnap.reflection;

import org.codnect.firesnap.reflection.binder.CompoundTypeBinder;
import org.codnect.firesnap.reflection.binder.TypeBinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Burak Koken on 24.5.2018.
 *
 * @author Burak Koken
 */
public class XClass extends XAnnotatedElement{

    private Class annotatedClass;
    private TypeBinder typeBinder;

    protected XClass(Class annotatedClass, ReflectionManager reflectionManager, TypeBinder typeBinder) {
        super(annotatedClass, reflectionManager);
        this.annotatedClass = annotatedClass;
        this.typeBinder = typeBinder;
    }

    /**
     * Get this annotated class name.
     *
     * @return this annotated class name
     */
    public String getName() {
        return annotatedClass.getName();
    }

    /**
     * Gets the modifiers for this element.
     *
     * @return the modifiers for this element.
     */
    public int getModifiers() {
        return annotatedClass.getModifiers();
    }

    /**
     * Determines if this element is an primitive type.
     *
     * @return if this element is an primitive type, it returns
     * true. Otherwise it returns false.
     */
    public boolean isPrimitive() {
        return annotatedClass.isPrimitive();
    }

    /**
     * Determines if this element is an enum.
     *
     * @return if this element is an enum, it returns
     * true. Otherwise it returns false.
     */
    public boolean isEnum() {
        return annotatedClass.isEnum();
    }

    /**
     * Determines if this element is an interface.
     *
     * @return if this element is an interface, it returns
     * true. Otherwise it returns false.
     */
    public boolean isInterface() {
        return annotatedClass.isInterface();
    }

    /**
     * Determines if this element is an array.
     *
     * @return if this element is an array, it returns
     * true. Otherwise it returns false.
     */
    boolean isArray() {
        return annotatedClass.isArray();
    }

    /**
     * Determines if this element is an abstract class.
     *
     * @return if this element is an abstract class, it returns
     * true. Otherwise it returns false.
     */
    public boolean isAbstract() {
        return Modifier.isAbstract(annotatedClass.getModifiers());
    }

    /**
     * Get all the declared fields for this element.
     *
     * @return all the declared fields
     */
    public List<XField> getDeclaredFields() {
        List<XField> fields = new LinkedList<>();
        for(Field field : annotatedClass.getDeclaredFields()) {
            fields.add(getReflectionManager().getXField(field, getTypeBinder()));
        }
        return fields;
    }

    /**
     * Get all the declared methods for this element.
     *
     * @return all the declared methods
     */
    public List<XMethod> getDeclaredMethods() {
        List<XMethod> methods = new LinkedList<>();

        for(Method method : annotatedClass.getDeclaredMethods()) {
            methods.add(getReflectionManager().getXMethod(method, getTypeBinder()));
        }

        return methods;
    }

    /**
     * Get all the declared field properties for this element.
     *
     * @return all the declared field properties
     */
    public List<XProperty> getDeclaredFieldProperties() {
        LinkedList<XProperty> fieldProperties = new LinkedList<>();

        for(Field field : annotatedClass.getDeclaredFields()) {
            if(ReflectionUtil.isProperty(field)) {
                fieldProperties.add(getReflectionManager().getXProperty(field, getTypeBinder()));
            }
        }

        return fieldProperties;
    }

    /**
     * Get all the declared method properties for this element.
     *
     * @return all the declared method properties
     */
    public List<XProperty> getDeclaredMethodProperties() {
        LinkedList<XProperty> methodProperties = new LinkedList<>();

        for(Method method : annotatedClass.getDeclaredMethods()) {
            if(ReflectionUtil.isProperty(method)) {
                methodProperties.add(getReflectionManager().getXProperty(method, getTypeBinder()));
            }
        }

        return methodProperties;
    }

    /**
     * Get the type binder for this XClass.
     *
     * @return the type binder for this XClass
     */
    public TypeBinder getTypeBinder() {
        return typeBinder;
    }

    /**
     * Get the this XClass's super class.
     *
     * @return the this XClass's super class
     */
    public XClass getSuperclass() {
        return getReflectionManager().getXClass(annotatedClass.getSuperclass(),
                CompoundTypeBinder.create(
                        getTypeBinder(),
                        getReflectionManager().getTypeBinder(annotatedClass)
                )
        );
    }

    /**
     * Returns a string representation of the XClass.
     *
     * @return a string representation of the XClass
     */
    @Override
    public String toString() {
        return annotatedClass.getName();
    }

}
