package org.codnect.firesnap.annotation;

/**
 * Created by Burak Koken on 18.9.2018.
 *
 * @author Burak Koken
 */
public enum AccessType {

    DEFAULT("method"),

    METHOD("method"),

    FIELD("field");

    String type;

    AccessType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
