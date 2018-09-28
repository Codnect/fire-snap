package org.codnect.firesnap.mapping;

import org.codnect.firesnap.core.MetadataContext;

/**
 * Created by Burak Koken on 28.9.2018.
 *
 * @author Burak Koken
 */
public class Property {

    private String name;
    private boolean nullable;
    private MetadataContext metadataContext;

    public Property(MetadataContext metadataContext) {
        nullable = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

}
