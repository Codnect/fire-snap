package org.codnect.firesnap.binder;

import org.codnect.firesnap.annotation.AccessType;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.mapping.Node;
import org.codnect.firesnap.mapping.SimpleValue;

/**
 * Created by Burak Koken on 25.10.2018.
 *
 * @author Burak Koken
 */
public class SimpleValueBinder {

    private String propertyName;
    private AccessType accessType;
    private String persistentClassName;
    private Node node;
    private SimpleValue value;
    private MetadataContext metadataContext;

    public SimpleValueBinder(MetadataContext metadataContext) {
        this.metadataContext = metadataContext;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public void setPersistentClassName(String persistentClassName) {
        this.persistentClassName = persistentClassName;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setMetadataContext(MetadataContext metadataContext) {
        this.metadataContext = metadataContext;
    }

    public SimpleValue bind() {
        value = new SimpleValue(node, metadataContext);
        return value;
    }

}
