package org.codnect.firesnap.reflection;

import java.util.List;

/**
 * Created by Burak Koken on 13.9.2018.
 *
 * @author Burak Koken
 */
public class Foo extends FooSuper{

    public static Integer staticField;
    String fieldProperty;

    public List<String> getCollectionProperty() {
        return null;
    }

    public Integer getMethodProperty() {
        return null;
    }

    public int getPrimitiveProperty() {
        return 0;
    }

    public static Integer getStaticField() {
        return null;
    }
}
