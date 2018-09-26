package org.codnect.firesnap.core;

import org.codnect.firesnap.annotation.Access;
import org.codnect.firesnap.annotation.AccessType;
import org.codnect.firesnap.annotation.MappedSuperClass;

/**
 * Created by Burak Koken on 25.9.2018.
 *
 * @author Burak Koken
 */
@MappedSuperClass
@Access(AccessType.METHOD)
public class FooParent {

    @Access(AccessType.FIELD)
    private int parentIdField;
    private String parentNameField;

    public int getParentIdMethod() {
        return 0;
    }

    public String getParentNameMethod() {
        return null;
    }

}
