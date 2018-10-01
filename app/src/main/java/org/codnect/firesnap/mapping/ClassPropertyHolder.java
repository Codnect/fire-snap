package org.codnect.firesnap.mapping;

import org.codnect.firesnap.binder.ModelBinder;
import org.codnect.firesnap.core.InheritanceState;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.reflection.XClass;

import java.util.Map;

/**
 * Created by Burak Koken on 27.9.2018.
 *
 * @author Burak Koken
 */
public class ClassPropertyHolder extends PropertyHolder {

    private PersistentClass persistentClass;
    private ModelBinder modelBinder;
    private Map<XClass, InheritanceState> inheritanceStateMap;

    public ClassPropertyHolder (PersistentClass persistentClass,
                                ModelBinder modelBinder,
                                Map<XClass, InheritanceState> inheritanceStateMap,
                                MetadataContext metadataContext) {
        super(persistentClass.getModelName(), metadataContext);
        this.persistentClass = persistentClass;
        this.modelBinder = modelBinder;
        this.inheritanceStateMap = inheritanceStateMap;
    }

    @Override
    public String getModelName() {
        return persistentClass.getModelName();
    }

    @Override
    public String getClassName() {
        return persistentClass.getClassName();
    }

    @Override
    public boolean isModel() {
        return true;
    }

    @Override
    public boolean isElement() {
        return false;
    }

    @Override
    public Node getNode() {
        return persistentClass.getNode();
    }

    @Override
    public PersistentClass getPersistentClass() {
        return persistentClass;
    }

}
