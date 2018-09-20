package org.codnect.firesnap.mapping;

import org.codnect.firesnap.core.MetadataContext;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public abstract class PersistentClass {

    private String aliasName;
    private String modelName;
    private String className;
    private MetadataContext metadataContext;

    public PersistentClass(MetadataContext metadataContext) {
        this.metadataContext = metadataContext;
    }

    /**
     * Get the alias name of the persistence class.
     *
     * @return the alias name of the persistence class
     */
    public String getAliasName() {
        return aliasName;
    }

    /**
     * Set the alias name of the persistence class.
     *
     * @param aliasName new alias name
     */
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    /**
     * Get the model name of the persistence class.
     *
     * @return the model name of the persistence class
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Set the model name of the persistence class.
     *
     * @param modelName new model name
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
     * Get the model name of the persistence class.
     *
     * @return the class name of the persistence class
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set the class name of the persistence class.
     *
     * @param className new class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

}
