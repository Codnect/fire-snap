package org.codnect.firesnap.mapping;

import org.codnect.firesnap.annotation.JoinProperty;
import org.codnect.firesnap.annotation.Property;
import org.codnect.firesnap.binder.ModelBinder;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.core.PropertyHolder;
import org.codnect.firesnap.reflection.XProperty;

/**
 * Created by Burak Koken on 7.10.2018.
 *
 * @author Burak Koken
 */
public class NodePropertyBuilder {

    private XProperty property;
    private PropertyData propertyData;
    private PropertyHolder propertyHolder;
    private ModelBinder modelBinder;
    private MetadataContext metadataContext;

    public NodePropertyBuilder(XProperty property,
                               PropertyData propertyData,
                               PropertyHolder propertyHolder,
                               ModelBinder modelBinder,
                               MetadataContext metadataContext) {
        this.property = property;
        this.propertyData = propertyData;
        this.propertyHolder = propertyHolder;
        this.modelBinder = modelBinder;
        this.metadataContext = metadataContext;
    }

    /**
     *
     */
    public void build() {
        if(property.isAnnotationPresent(Property.class)) {
            Property propertyAnnotation = property.getAnnotation(Property.class);
            NodeProperty nodeProperty = NodeProperty.createNodePropertyFromAnnotation(
                    propertyAnnotation,
                    propertyData,
                    propertyHolder,
                    metadataContext
            );
        }
        if(property.isAnnotationPresent(JoinProperty.class)) {
            JoinProperty joinPropertyAnnotation = property.getAnnotation(JoinProperty.class);
            NodeJoinProperty nodeJoinProperty = null;
        }
        /* code */
    }

}
