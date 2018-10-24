package org.codnect.firesnap.mapping;

import org.codnect.firesnap.core.MetadataContext;

/**
 * Created by Burak Koken on 24.10.2018.
 *
 * @author Burak Koken
 */
public class SimpleValue {

    private MetadataContext metadataContext;
    private Node node;

    public SimpleValue(MetadataContext metadataContext) {
        this.metadataContext = metadataContext;
    }

    public SimpleValue(Node node, MetadataContext metadataContext) {
        this(metadataContext);
        this.node = node;
    }

    /**
     *
     * @return
     */
    public MetadataContext getMetadataContext() {
        return metadataContext;
    }

    /**
     *
     * @return
     */
    public Node getNode() {
        return node;
    }

}
