package org.codnect.firesnap.mapping;

import org.codnect.firesnap.annotation.Discriminator;
import org.codnect.firesnap.annotation.DiscriminatorType;
import org.codnect.firesnap.core.MetadataContext;

/**
 * Created by Burak Koken on 28.9.2018.
 *
 * @author Burak Koken
 */
public class DiscriminatorNodeProperty extends NodeProperty {

    public static final String DEFAULT_PROPERTY_VALUE = "DISCTYPE";
    private DiscriminatorType discriminatorType;

    private DiscriminatorNodeProperty(MetadataContext metadataContext) {
        super(metadataContext);
    }

    /**
     *
     * @param discriminatorAnnotation
     * @param metadataContext
     * @return
     */
    public static DiscriminatorNodeProperty createDiscriminatorProperty(Discriminator discriminatorAnnotation,
                                                                        MetadataContext metadataContext) {
        DiscriminatorNodeProperty discriminatorNodeProperty = new DiscriminatorNodeProperty(metadataContext);
        if(discriminatorAnnotation != null) {
            discriminatorNodeProperty.setDiscriminatorType(discriminatorAnnotation.discriminatorType());
            String propertyName = discriminatorAnnotation.name();
            if(propertyName == null) {
                discriminatorNodeProperty.setName(DEFAULT_PROPERTY_VALUE);
            }
        } else {
            discriminatorNodeProperty.setDiscriminatorType(DiscriminatorType.STRING);
            discriminatorNodeProperty.setName(DEFAULT_PROPERTY_VALUE);
        }
        return discriminatorNodeProperty;
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

    /**
     *
     * @return
     */
    public String getDiscriminatorTypeName() {
        if(discriminatorType == DiscriminatorType.STRING) {
            return "string";
        }
        return "integer";
    }

}
