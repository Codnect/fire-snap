package org.codnect.firesnap.mapping;

import org.codnect.firesnap.core.MetadataContext;

/**
 * Created by Burak Koken on 16.10.2018.
 *
 * @author Burak Koken
 */
public class ManyToOne extends ToOne {

    private boolean isLogicalOneToOne;

    public ManyToOne(Node node, MetadataContext metadataContext) {
        super(node, metadataContext);
    }

    /**
     *
     * @return
     */
    public boolean isLogicalOneToOne() {
        return isLogicalOneToOne;
    }

    /**
     *
     * @param logicalOneToOne
     */
    public void setLogicalOneToOne(boolean logicalOneToOne) {
        isLogicalOneToOne = logicalOneToOne;
    }

}
