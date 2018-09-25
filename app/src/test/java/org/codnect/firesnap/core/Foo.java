package org.codnect.firesnap.core;

import org.codnect.firesnap.annotation.Access;
import org.codnect.firesnap.annotation.AccessType;
import org.codnect.firesnap.annotation.Model;

/**
 * Created by Burak Koken on 25.9.2018.
 *
 * @author Burak Koken
 */
@Model
@Access(AccessType.FIELD)
public class Foo extends FooParent{

    private int idField;
    private String nameField;

    @Access(AccessType.METHOD)
    public int getIdMethod() {
        return 0;
    }

    public String getNameMethod() {
        return null;
    }

}
