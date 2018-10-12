package org.codnect.firesnap.core;

import org.codnect.firesnap.inheritance.PersistentClass;
import org.codnect.firesnap.mapping.Node;

/**
 * Created by Burak Koken on 27.9.2018.
 *
 * @author Burak Koken
 */
public abstract class PropertyHolder {

    private String path;
    private MetadataContext metadataContext;

    public PropertyHolder(String path,
                          MetadataContext metadataContext) {
        this.path = path;
        this.metadataContext = metadataContext;
    }

    /**
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     *
     * @return
     */
    protected MetadataContext getMetadataContext() {
        return metadataContext;
    }

    /**
     *
     * @return
     */
    public abstract String getModelName();

    /**
     *
     * @return
     */
    public abstract String getClassName();

    /**
     *
     * @return
     */
    public abstract boolean isModel();

    /**
     *
     * @return
     */
    public abstract boolean isElement();

    /**
     *
     * @return
     */
    public abstract Node getNode();

    /**
     *
     * @return
     */
    public abstract PersistentClass getPersistentClass();

}
