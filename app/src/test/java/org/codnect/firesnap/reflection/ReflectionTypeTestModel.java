package org.codnect.firesnap.reflection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burak Koken on 3.6.2018.
 *
 * @author Burak Koken
 */
public class ReflectionTypeTestModel<E> {

    public Object getObjectProperty() {
        return null;
    }

    public int getPrimitiveProperty() {
        return 0;
    }

    public String[] getArrayProperty() {
        return null;
    }

    public List<String>[] getGenericArrayTypeProperty() {
        return new ArrayList[0];
    }

    public List<Integer> getCollectionProperty() {
        return new ArrayList<>();
    }

    public List<? extends Integer> getWilcardTypeProperty() {
        return null;
    }

    public E getTypeVariableProperty() {
        return null;
    }

}
