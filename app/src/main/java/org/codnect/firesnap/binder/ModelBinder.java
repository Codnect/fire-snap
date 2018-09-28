package org.codnect.firesnap.binder;

import org.codnect.firesnap.annotation.Model;
import org.codnect.firesnap.core.InheritanceState;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.mapping.PersistentClass;
import org.codnect.firesnap.reflection.XClass;

/**
 * Created by Burak Koken on 14.5.2018.
 *
 * @author Burak Koken
 */
public class ModelBinder {

    private String name;
    private XClass annotatedClass;
    private Model modelAnnotation;
    private MetadataContext metadataContext;
    private PersistentClass persistentClass;
    private InheritanceState inheritanceState;
    private String discriminatorValue;

    public ModelBinder(XClass annotatedClass, Model modelAnnotation, PersistentClass persistentClass,
                       MetadataContext metadataContext) {
        this.annotatedClass = annotatedClass;
        this.modelAnnotation = modelAnnotation;
        this.persistentClass = persistentClass;
        this.metadataContext = metadataContext;
    }

    /**
     * Bind the model.
     */
    public void bindModel() {
        if(BinderHelper.isEmptyAnnotationValue(modelAnnotation.value())) {
            persistentClass.setAliasName(annotatedClass.getName());
        }
        else {
            name = modelAnnotation.value();
            persistentClass.setAliasName(modelAnnotation.value());
        }
        persistentClass.setModelName(annotatedClass.getName());
        persistentClass.setClassName(annotatedClass.getName());

        String aliasName = persistentClass.getAliasName();
        String modelName = persistentClass.getModelName();
        metadataContext.getMetadataCollector().addModelAliasName(modelName, modelName);
        if(!modelName.equals(aliasName)) {
            metadataContext.getMetadataCollector().addModelAliasName(aliasName, modelName);
        }
    }

    public InheritanceState getInheritanceState() {
        return inheritanceState;
    }

    public void setInheritanceState(InheritanceState inheritanceState) {
        this.inheritanceState = inheritanceState;
    }

    public String getDiscriminatorValue() {
        return discriminatorValue;
    }

    public void setDiscriminatorValue(String discriminatorValue) {
        this.discriminatorValue = discriminatorValue;
    }

}
