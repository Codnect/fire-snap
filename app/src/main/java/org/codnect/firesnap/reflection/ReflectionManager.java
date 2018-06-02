package org.codnect.firesnap.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * Created by Burak Koken on 27.5.2018.
 *
 * @author Burak Koken
 */
public class ReflectionManager {

    public ReflectionManager() {

    }

    /**
     * Get the instance of the XClass for specified annotated
     * class.
     *
     * @param annotatedClass annotated class
     * @return the instance of the XClass for specified annotated
     * class
     */
    public XClass getXClass(Class annotatedClass) {
        return new XClass(annotatedClass, this);
    }

    /**
     * Converts a XClass to a Class.
     *
     * @param xClass a XField
     * @return a Class
     */
    public Class toClass(XClass xClass) {
        return (Class) xClass.toAnnotatedElement();
    }

    /**
     * Get the instance of the XMethod for specified Field
     * class member.
     *
     * @param member an instance of the Field
     * @return  the instance of the XField for Field
     */
    public XField getXField(Member member) {

        if(!(member instanceof Field)) {
            throw new IllegalArgumentException("The member should be a Field instance for XField");
        }

        return new XField(member);
    }

    /**
     * Converts a XField to a Field.
     *
     * @param xField a XField
     * @return a Field
     */
    public Field toField(XField xField) {
        return (Field) xField.toAnnotatedElement();
    }

    /**
     * Get the instance of the XMethod for specified Method
     * class member.
     *
     * @param member an instance of the Method
     * @return the instance of the XMethod for Method
     */
    public XMethod getXMethod(Member member) {

        if(!(member instanceof Method)) {
            throw new IllegalArgumentException("The member should be a Method instance for XMethod");
        }

        return new XMethod(member);
    }

    /**
     * Converts a XMethod to a Method.
     *
     * @param xMethod a XMethod
     * @return a Method
     */
    public Method toMethod(XMethod xMethod) {
        return (Method) xMethod.toAnnotatedElement();
    }

    /**
     * Get the instance of the XProperty for specified field or
     * method class member.
     *
     * @param member an instance of the Field or the Method
     * @return the instance of the XProperty for Field or Method
     */
    public XProperty getXProperty(Member member) {

        if(!(member instanceof Field) && !(member instanceof Method)) {
            throw new IllegalArgumentException("The member should be a Field or Method instance for XProperty");
        }

        return new XProperty(member);

    }

}
