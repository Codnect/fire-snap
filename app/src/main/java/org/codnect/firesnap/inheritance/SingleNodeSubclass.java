package org.codnect.firesnap.inheritance;

import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.mapping.Node;
import org.codnect.firesnap.mapping.SimpleValue;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public class SingleNodeSubclass extends Subclass {

    public SingleNodeSubclass(MetadataContext metadataContext, PersistentClass superModel) {
        super(metadataContext, superModel);
    }

    @Override
    public boolean isNodeOwner() {
        return false;
    }

    @Override
    public void setNode(Node node) {

    }

    @Override
    public Node getNode() {
        return null;
    }

    @Override
    public SimpleValue getDiscriminator() {
        return null;
    }

}
