package org.codnect.firesnap.inheritance;

import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.mapping.Node;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public class RootClass extends PersistentClass {

    private Node node;
    private Class persisterClass;

    public RootClass(MetadataContext metadataContext) {
        super(metadataContext);
    }

    @Override
    public boolean isNodeOwner() {
        return true;
    }

    @Override
    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public PersistentClass getSuperModelClass() {
        return null;
    }

    @Override
    public Node getRootNode() {
        return node;
    }

    @Override
    public RootClass getRootClass() {
        return this;
    }

    @Override
    public Class getPersisterClass() {
        return persisterClass;
    }

    @Override
    public void setPersisterClass(Class persisterClass) {
        this.persisterClass = persisterClass;
    }

}
