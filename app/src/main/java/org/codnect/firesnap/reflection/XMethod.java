package org.codnect.firesnap.reflection;

import java.lang.reflect.Member;

/**
 * Created by Burak Koken on 24.5.2018.
 *
 * @author Burak Koken
 */
public class XMethod extends XMember {

    protected XMethod(Member member) {
        super(member);
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
     * @param target the method object
     * @return if this object is a method instance, it
     * invokes the method and returns method return value.
     */
    @Override
    public Object invoke(Object target) {
        return null;
    }

    /**
     * Invokes the method.
     *
     * @param target the method object
     * @param parameters parameters to pass
     * @return if this object is a method instance, it
     * invokes the method and returns method return value.
     */
    @Override
    public Object invoke(Object target, Object... parameters) {
        return null;
    }

}
