package org.codnect.firesnap.reflection;

import org.codnect.firesnap.reflection.binder.TypeBinder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by Burak Koken on 24.5.2018.
 *
 * @author Burak Koken
 */
public abstract class XMember extends XAnnotatedElement{

    private Type type;
    private XType xType;
    private TypeBinder typeBinder;

    public XMember(Member member,
                   Type type,
                   XType xType,
                   TypeBinder typeBinder,
                   ReflectionManager reflectionManager) {
        super((AnnotatedElement) member, reflectionManager);
        this.type = type;
        this.xType = xType;
        this.typeBinder = typeBinder;
    }

    /**
     * Get the member name.
     *
     * @return the member name
     */
    public abstract String getName();

    /**
     * Get the member instance.
     *
     * @return the member instance
     */
    public Member getMember() {
        return (Member) toAnnotatedElement();
    }

    /**
     * Get the modifiers for this member.
     *
     * @return the modifiers for this member.
     */
    public int getModifiers() {
        return getMember().getModifiers();
    }

    /**
     * Get the java type for this member.
     *
     * @return the java type for this member.
     */
    public Type getJavaType() {
        return typeBinder.bind(type);
    }

    /**
     * Get the type for this member.
     *
     * @return the type for this member.
     */
    public XClass getType() {
        return xType.getType();
    }

    /**
     * Get the type binder for this member.
     *
     * @return the type binder for this member.
     */
    public TypeBinder getTypeBinder() {
        return typeBinder;
    }

    /**
     * Get the declaring class for this member.
     *
     * @return the declaring class for this member.
     */
    public XClass getDeclaringClass() {
        return getReflectionManager().getXClass(getMember().getDeclaringClass());
    }

    /**
     * Determines if this type is an array.
     *
     * @return if this type is an array, it returns
     * true. Otherwise, it returns false.
     */
    public boolean isArray() {
        return xType.isArray();
    }

    /**
     * Determines if this type is a collection.
     *
     * @return if this type is a collection, it returns
     * true. Otherwise, it returns false.
     */
    public boolean isCollection() {
        return xType.isCollection();
    }

    /**
     * Get the instance XClass of the element class for this type.
     * The type of this property's elements for arrays and maps,
     * the type of the property itself for everything else.
     *
     * @return the instance XClass of the element class
     */
    public XClass getElementClass() {
        return xType.getElementClass();
    }

    /**
     * Get this element's collection class if element type is a
     * collection. Otherwise returns null value.
     *
     * @return this element's collection class
     */
    public Class getCollectionClass() {
        return xType.getCollectionClass();
    }

    /**
     * Get the class or element class for this element.
     *
     * @return  the class or element class
     */
    public XClass getClassOrElementClass() {
        return xType.getClassOrElementClass();
    }

    /**
     * Get the instance XClass of the map key type if this
     * element type is a map. Otherwise returns null value.
     *
     * @return the instance XClass of map key
     */
    public XClass getMapKey() {
        return xType.getMapKey();
    }

    /**
     * Determines if this member is a resolved type.
     *
     * @return if this member is a resolved type, it
     * returns true. Otherwise, it returns false.
     */
    public final boolean isTypeResolved() {
        return xType.isResolved();
    }

    /**
     * Set the member access flag.
     *
     * @param accessible access flag
     */
    public void setAccessible(boolean accessible) {
        ((AccessibleObject)getMember()).setAccessible(accessible);
    }

}
