package org.codnect.firesnap.mapping;

import org.codnect.firesnap.annotation.JoinProperty;
import org.codnect.firesnap.binder.BinderHelper;
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

    /**
     *
     * @param joinPropertyAnnotation
     */
    public void setJoinPropertyAnnotation(JoinProperty joinPropertyAnnotation) {
        if(!BinderHelper.isEmptyAnnotationValue(joinPropertyAnnotation.name())) {
            setName(joinPropertyAnnotation.name());
        }
    }

    /**
     *
     * @param joinPropertyAnnotation
     * @param propertyData
     * @param propertyHolder
     * @param metadataContext
     * @return
     */
    public static NodeJoinProperty createNodeJoinPropertyFromAnnotation(JoinProperty joinPropertyAnnotation,
                                                                PropertyData propertyData,
                                                                PropertyHolder propertyHolder,
                                                                MetadataContext metadataContext) {
        if(joinPropertyAnnotation != null) {
            return createNodeJoinProperty(joinPropertyAnnotation,
                    propertyData,
                    propertyHolder,
                    metadataContext);
        }
        return createNodeJoinProperty(null, propertyData, propertyHolder, metadataContext);
    }

    /**
     *
     * @param joinPropertyAnnotation
     * @param propertyData
     * @param propertyHolder
     * @param metadataContext
     * @return
     */
    private static NodeJoinProperty createNodeJoinProperty(JoinProperty joinPropertyAnnotation,
                                                           PropertyData propertyData,
                                                           PropertyHolder propertyHolder,
                                                           MetadataContext metadataContext) {
        NodeJoinProperty nodeJoinProperty = new NodeJoinProperty(metadataContext);
        nodeJoinProperty.setPropertyHolder(propertyHolder);
        nodeJoinProperty.setPropertyName(BinderHelper.getRelativePath(propertyData.getPropertyName(), propertyHolder));
        if(joinPropertyAnnotation != null) {
            nodeJoinProperty.setJoinPropertyAnnotation(joinPropertyAnnotation);
        }
        return nodeJoinProperty;
    }

}
