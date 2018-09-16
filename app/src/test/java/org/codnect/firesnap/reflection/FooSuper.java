package org.codnect.firesnap.reflection;

import java.util.List;

/**
 * Created by Burak Koken on 14.9.2018.
 *
 * @author Burak Koken
 */
public class FooSuper<E> {

    public int superField;

    public boolean isSuperMethod() {
        return false;
    }

    public E getParameterizedProperty() {
        return null;
    }

    public List<E> getParameterizedCollectionProperty() {
        return null;
    }

}
