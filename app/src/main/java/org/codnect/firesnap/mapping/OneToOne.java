package org.codnect.firesnap.mapping;

import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.inheritance.PersistentClass;

/**
 * Created by Burak Koken on 16.10.2018.
 *
 * @author Burak Koken
 */
public class OneToOne extends ToOne {

    private String propertyName;
    private String modelName;

    public OneToOne(Node node, PersistentClass persistentClass, MetadataContext metadataContext) {
        super(node, metadataContext);
    }

    /**
     *
     * @return
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     *
     * @param propertyName
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     *
     * @return
     */
    public String getModelName() {
        return modelName;
    }

    /**
     *
     * @param modelName
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}
