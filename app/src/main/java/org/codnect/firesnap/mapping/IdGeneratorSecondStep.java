package org.codnect.firesnap.mapping;

import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.reflection.XProperty;

/**
 * Created by Burak Koken on 25.10.2018.
 *
 * @author Burak Koken
 */
public class IdGeneratorSecondStep implements SecondStep {

    private SimpleValue idPropertyValue;
    private XProperty property;
    private IdGeneratorType idGeneratorType;
    private MetadataContext metadataContext;

    public IdGeneratorSecondStep(SimpleValue idPropertyValue,
                                 XProperty property,
                                 IdGeneratorType idGeneratorType,
                                 MetadataContext metadataContext) {
        this.idPropertyValue = idPropertyValue;
        this.property = property;
        this.idGeneratorType = idGeneratorType;
        this.metadataContext = metadataContext;
    }

    @Override
    public void handle() {
        /* TODO */
    }

}
