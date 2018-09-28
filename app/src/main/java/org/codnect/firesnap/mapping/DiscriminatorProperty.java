package org.codnect.firesnap.mapping;

import org.codnect.firesnap.annotation.Discriminator;
import org.codnect.firesnap.annotation.DiscriminatorType;
import org.codnect.firesnap.core.MetadataContext;

/**
 * Created by Burak Koken on 28.9.2018.
 *
 * @author Burak Koken
 */
public class DiscriminatorProperty extends Property{

    public static final String DEFAULT_PROPERTY_VALUE = "DISCTYPE";
    private DiscriminatorType discriminatorType;

    private DiscriminatorProperty(MetadataContext metadataContext) {
        super(metadataContext);
    }

    /**
     *
     * @param discriminatorAnnotation
     * @param metadataContext
     * @return
     */
    public static DiscriminatorProperty createDiscriminatorProperty(Discriminator discriminatorAnnotation,
                                                                    MetadataContext metadataContext) {
        DiscriminatorProperty discriminatorProperty = new DiscriminatorProperty(metadataContext);
        if(discriminatorAnnotation != null) {
            discriminatorProperty.setDiscriminatorType(discriminatorAnnotation.discriminatorType());
            String propertyName = discriminatorAnnotation.name();
            if(propertyName == null) {
                discriminatorProperty.setName(DEFAULT_PROPERTY_VALUE);
            }
        } else {
            discriminatorProperty.setDiscriminatorType(DiscriminatorType.STRING);
            discriminatorProperty.setName(DEFAULT_PROPERTY_VALUE);
        }
        return discriminatorProperty;
    }

    /**
     *
     * @return
     */
    public DiscriminatorType getDiscriminatorType() {
        return discriminatorType;
    }

    /**
     *
     * @param discriminatorType
     */
    public void setDiscriminatorType(DiscriminatorType discriminatorType) {
        this.discriminatorType = discriminatorType;
    }

}
