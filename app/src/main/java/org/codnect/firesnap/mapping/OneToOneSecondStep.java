package org.codnect.firesnap.mapping;

import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.core.PropertyHolder;

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
                metadataContext
        );
        String propertyName = propertyData.getPropertyName();
        /* code... */
    }

}
