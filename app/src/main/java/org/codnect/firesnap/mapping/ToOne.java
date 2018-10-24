package org.codnect.firesnap.mapping;

import org.codnect.firesnap.core.MetadataContext;

/**
 * Created by Burak Koken on 14.10.2018.
 *
 * @author Burak Koken
 */
public abstract class ToOne extends SimpleValue {

    private String referencedModelName;
    private String referencedPropertyName;

    protected ToOne(Node node, MetadataContext metadataContext) {
        super(node, metadataContext);
    }

    /**
     *
     * @return
     */
    public String getReferencedModelName() {
        return referencedModelName;
    }

    /**
     *
     * @param referencedModelName
     */
    public void setReferencedModelName(String referencedModelName) {
        this.referencedModelName = referencedModelName;
    }

    /**
     *
     * @return
     */
    public String getReferencedPropertyName() {
        return referencedPropertyName;
    }

    /**
     *
     * @param referencedPropertyName
     */
    public void setReferencedPropertyName(String referencedPropertyName) {
        this.referencedPropertyName = referencedPropertyName;
    }

}
