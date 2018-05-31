package org.codnect.firesnap.reflection;

import org.codnect.firesnap.exception.MappingException;

import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * Created by Burak Koken on 24.5.2018.
 *
 * @author Burak Koken
 */
public class XProperty extends XMember {

    protected XProperty(Member member) {
        super(member);
    }

    /**
     * Get the name of the property.
     *
     * @return property name
     */
    @Override
    public String getName() {
        String propertyName = getMember().getName();

        if(getMember() instanceof Method) {
            if(propertyName.startsWith("get")) {
                return ReflectionUtil.decapitalize(propertyName);
            }
            else if(propertyName.startsWith("is")) {
                return ReflectionUtil.decapitalize(propertyName);
            }
            throw new MappingException("Method (" + propertyName + ") is not a property getter.");
        }

        return propertyName;
    }

    /**
     * Returns a string representation of the XProperty.
     *
     * @return a string representation of the XProperty
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * Invokes the field or method.
     *
     * @param target the field or method object
     * @return if this object is a field instance, this method
     * returns the field value. Otherwise, it invokes the
     * method and returns method return value.
     */
    @Override
    public Object invoke(Object target) {
        return null;
    }


    /**
     * Invokes the field or method.
     *
     * @param target the field or method object
     * @param parameters parameters to pass
     * @return if this object is a field instance, this method
     * returns the field value. Otherwise, it invokes the
     * method and returns method return value.
     */
    @Override
    public Object invoke(Object target, Object... parameters) {
        return null;
    }

}
