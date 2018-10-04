package org.codnect.firesnap.inheritance;

import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.mapping.Node;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public abstract class Subclass extends PersistentClass {

    private PersistentClass superModel;
    private Class persisterClass;

    public Subclass(MetadataContext metadataContext, PersistentClass superModel) {
        super(metadataContext);
        this.superModel = superModel;
    }

    @Override
    public Class getPersisterClass() {
        if(persisterClass != null) {
            return persisterClass;
        }
        return getSuperModelClass().getPersisterClass();
    }

    @Override
    public void setPersisterClass(Class persisterClass) {
        this.persisterClass = persisterClass;
    }

    @Override
    public PersistentClass getSuperModelClass() {
        return superModel;
    }

    @Override
    public Node getRootNode() {
        return superModel.getRootNode();
    }

    @Override
    public RootClass getRootClass() {
        return superModel.getRootClass();
    }

}
