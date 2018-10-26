package org.codnect.firesnap.mapping;

import org.codnect.firesnap.core.MetadataContext;

/**
 * Created by Burak Koken on 24.10.2018.
 *
 * @author Burak Koken
 */
public class SimpleValue {

    private Node node;
    private String typeName;
    private MetadataContext metadataContext;

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
    public Node getNode() {
        return node;
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
     * @param typeName
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     *
     * @return
     */
    public String getTypeName() {
        return typeName;
    }

}
