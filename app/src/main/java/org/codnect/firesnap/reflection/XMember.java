package org.codnect.firesnap.reflection;

import org.codnect.firesnap.reflection.binder.TypeBinder;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

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
     *
     * @return
     */
    public Type getJavaType() {
        return typeBinder.bind(type);
    }

    /**
     *
     * @return
     */
    public XClass getType() {
        return null;
    }

    /**
     *
     * @return
     */
    public TypeBinder getTypeBinder() {
        return typeBinder;
    }

}
