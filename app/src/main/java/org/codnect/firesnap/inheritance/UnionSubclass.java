package org.codnect.firesnap.inheritance;

import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.mapping.Node;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public class UnionSubclass extends Subclass {

    private Node node;

    public UnionSubclass(MetadataContext metadataContext, PersistentClass superModel) {
        super(metadataContext, superModel);
    }

    @Override
    public boolean isNodeOwner() {
        return true;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public void setNode(Node node) {
        this.node = node;
    }

}
