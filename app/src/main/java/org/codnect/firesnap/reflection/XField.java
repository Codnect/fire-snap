package org.codnect.firesnap.reflection;

import java.lang.reflect.Member;

/**
 * Created by Burak Koken on 24.5.2018.
 *
 * @author Burak Koken
 */
public class XField extends XMember {

    protected XField(Member member) {
        super(member);
    }

    /**
     * Get the XField member name.
     *
     * @return the XField member name.
     */
    @Override
    public String getName() {
        return getMember().getName();
    }

    /**
     * Returns a string representation of the XField.
     *
     * @return a string representation of the XField
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * Invokes the field.
     *
     * @param target the field object
     * @return if this object is a field instance, this method
     * returns the field value.
     */
    @Override
    public Object invoke(Object target) {
        return null;
    }

    /**
     * Invokes the field.
     *
     * @param target the field object
     * @param parameters parameters to pass
     * @return if this object is a field instance, this method
     * returns the field value.
     */
    @Override
    public Object invoke(Object target, Object... parameters) {
        return null;
    }

}
