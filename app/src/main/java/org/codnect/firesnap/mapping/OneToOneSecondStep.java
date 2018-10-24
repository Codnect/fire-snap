package org.codnect.firesnap.mapping;

import org.codnect.firesnap.binder.BinderHelper;
import org.codnect.firesnap.core.MetadataCollector;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.core.PropertyHolder;
import org.codnect.firesnap.exception.MappingException;
import org.codnect.firesnap.inheritance.PersistentClass;
import org.codnect.firesnap.util.StringHelper;

/**
 * Created by Burak Koken on 16.10.2018.
 *
 * @author Burak Koken
 */
public class OneToOneSecondStep implements SecondStep {

    private PropertyData propertyData;
    private PropertyHolder propertyHolder;
    private String mappedBy;
    private NodeJoinProperty nodeJoinProperty;
    private MetadataContext metadataContext;

    public OneToOneSecondStep(PropertyData propertyData,
                              PropertyHolder propertyHolder,
                              String mappedBy,
                              NodeJoinProperty nodeJoinProperty,
                              MetadataContext metadataContext) {
        this.propertyData = propertyData;
        this.propertyHolder = propertyHolder;
        this.mappedBy = mappedBy;
        this.nodeJoinProperty = nodeJoinProperty;
        this.metadataContext = metadataContext;
    }

    @Override
    public void handle() {
        org.codnect.firesnap.mapping.OneToOne oneToOne = new org.codnect.firesnap.mapping.OneToOne(
                propertyHolder.getNode(),
                propertyHolder.getPersistentClass(),
                metadataContext
        );
        String propertyName = propertyData.getPropertyName();
        oneToOne.setPropertyName(propertyName);
        String referenceModelName = propertyData.getClassOrElementName();
        oneToOne.setReferencedModelName(referenceModelName);

        MetadataCollector metadataCollector = metadataContext.getMetadataCollector();
        if(BinderHelper.isEmptyAnnotationValue(mappedBy)) {
            String propertyPath = StringHelper.qualify(propertyHolder.getPath(), propertyName);
            /* Create a node key */
        } else {
            PersistentClass otherSidePersistentClass = metadataCollector.getModelBinding(referenceModelName);
            if(otherSidePersistentClass == null) {
                throw new MappingException("");
            }
            NodeProperty otherSideNodeProperty = null;
            if(otherSideNodeProperty == null) {

            }
        }
    }

}
