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
    private Class persistentClass;

    public Subclass(MetadataContext metadataContext, PersistentClass superModel) {
        super(metadataContext);
        this.superModel = superModel;
    }

    @Override
    public Class getPersistentClass() {
        if(persistentClass != null) {
            return persistentClass;
        }
        return getSuperModelClass().getPersistentClass();
    }

    @Override
    public void setPersistentClass(Class persistentClass) {
        this.persistentClass = persistentClass;
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
