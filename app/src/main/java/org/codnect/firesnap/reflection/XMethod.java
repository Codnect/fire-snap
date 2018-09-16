package org.codnect.firesnap.reflection;

import org.codnect.firesnap.reflection.binder.TypeBinder;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by Burak Koken on 24.5.2018.
 *
 * @author Burak Koken
 */
public class XMethod extends XMember {

    private XMethod(Member member,
                    Type type,
                    XType xType,
                    TypeBinder typeBinder,
                    ReflectionManager reflectionManager) {
        super(member, type, xType, typeBinder, reflectionManager);
    }

    /**
     * Create a new instance of the XMethod for specified member.
     *
     * @param member an instance of Method
     * @param typeBinder an instance of type binder
     * @param reflectionManager reflection manager
     * @return a new instance of the XMethod for specified member.
     */
    public static XMethod create(Member member, TypeBinder typeBinder, ReflectionManager reflectionManager) {
        if(!(member instanceof Method)) {
            throw new IllegalArgumentException("The member should be a Method instance for XMethod");
        }
        Type type = typeBinder.bind(((Method)member).getGenericReturnType());
        XType xType = reflectionManager.getXType(typeBinder, type);
        return new XMethod(member, type, xType, typeBinder, reflectionManager);
    }

    /**
     * Get the XMethod member name.
     *
     * @return the XMethod member name.
     */
    @Override
    public String getName() {
        return getMember().getName();
    }

    /**
     * Returns a string representation of the XMethod.
     *
     * @return a string representation of the XMethod
     */
    @Override
    public String toString() {
        return getName();
    }


    /**
     * Invokes the method.
     *
     * @param object the method object
     * @return if this object is a method instance, it
     * invokes the method and returns method return value.
     */
    public Object invoke(Object object) {
        Object value = null;

        try {
            value =  ((Method) getMember()).invoke(object, new Object[0]);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invoking " + getName() + " on a null object", e);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Invoking " + getName() + " with wrong parameters", e );
        } catch (Exception e) {
            throw new IllegalStateException("Unable to invoke " + getName(), e);
        }

        return value;
    }

    /**
     * Invokes the method.
     *
     * @param object the method object
     * @param parameters parameters to pass
     * @return if this object is a method instance, it
     * invokes the method and returns method return value.
     */
    public Object invoke(Object object, Object... parameters) {
        Object value = null;

        try {
            value =  ((Method) getMember()).invoke(object, parameters);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invoking " + getName() + " on a null object", e);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Invoking " + getName() + " with wrong parameters", e );
        } catch (Exception e) {
            throw new IllegalStateException("Unable to invoke " + getName(), e);
        }

        return value;
    }

}
