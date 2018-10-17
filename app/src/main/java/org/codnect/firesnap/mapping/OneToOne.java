package org.codnect.firesnap.mapping;

import org.codnect.firesnap.core.MetadataContext;

/**
 * Created by Burak Koken on 16.10.2018.
 *
 * @author Burak Koken
 */
public class OneToOne extends ToOne {

    private String propertyName;
    private String modelName;

    public OneToOne(MetadataContext metadataContext) {
        super(metadataContext);
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}
