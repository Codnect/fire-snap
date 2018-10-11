package org.codnect.firesnap.mapping;

import org.codnect.firesnap.annotation.Property;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.core.PropertyHolder;

/**
 * Created by Burak Koken on 11.10.2018.
 *
 * @author Burak Koken
 */
public class NodeJoinProperty extends NodeProperty {

    private NodeJoinProperty(MetadataContext metadataContext) {
        super(metadataContext);
    }

    public static NodeJoinProperty createNodeJoinPropertyFromAnnotation(Property propertyAnnotation,
                                                                PropertyData propertyData,
                                                                PropertyHolder propertyHolder,
                                                                MetadataContext metadataContext) {
        return null;
    }

}
