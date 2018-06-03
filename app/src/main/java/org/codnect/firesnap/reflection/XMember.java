package org.codnect.firesnap.reflection;

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

    public XMember(Member member) {
        super((AnnotatedElement) member);
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

}
