package org.codnect.firesnap.mapping;

import org.codnect.firesnap.core.MetadataContext;

/**
 * Created by Burak Koken on 14.10.2018.
 *
 * @author Burak Koken
 */
public abstract class ToOne {

    private String referencedModelName;
    private String referencedPropertyName;
    private MetadataContext metadataContext;

    protected ToOne(MetadataContext metadataContext) {
        this.metadataContext = metadataContext;
    }

    public String getReferencedModelName() {
        return referencedModelName;
    }

    public void setReferencedModelName(String referencedModelName) {
        this.referencedModelName = referencedModelName;
    }

    public String getReferencedPropertyName() {
        return referencedPropertyName;
    }

    public void setReferencedPropertyName(String referencedPropertyName) {
        this.referencedPropertyName = referencedPropertyName;
    }

}
