package org.codnect.firesnap.mapping;

import org.codnect.firesnap.annotation.Property;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.core.PropertyHolder;
import org.codnect.firesnap.util.StringHelper;

/**
 * Created by Burak Koken on 28.9.2018.
 *
 * @author Burak Koken
 */
public class NodeProperty {

    private String name;
    private String propertyName;
    private boolean nullable;
    private PropertyHolder propertyHolder;
    private MetadataContext metadataContext;

    protected NodeProperty(MetadataContext metadataContext) {
        this.metadataContext = metadataContext;
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

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public PropertyHolder getPropertyHolder() {
        return propertyHolder;
    }

    public void setPropertyHolder(PropertyHolder propertyHolder) {
        this.propertyHolder = propertyHolder;
    }

    public void setMetadataContext(MetadataContext metadataContext) {
        this.metadataContext = metadataContext;
    }

    public static NodeProperty createNodePropertyFromAnnotation(Property propertyAnnotation,
                                                                PropertyData propertyData,
                                                                PropertyHolder propertyHolder,
                                                                MetadataContext metadataContext) {

        NodeProperty nodeProperty = new NodeProperty(metadataContext);
        String name;
        if(StringHelper.isEmpty(propertyAnnotation.name())) {
            name = propertyData.getPropertyName();
        } else {
            name = propertyAnnotation.name();
        }
        nodeProperty.setName(name);
        nodeProperty.setPropertyName(propertyData.getPropertyName());
        return nodeProperty;
    }

}
