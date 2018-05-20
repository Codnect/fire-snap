package org.codnect.firesnap.binder;

import org.codnect.firesnap.annotation.Model;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.mapping.PersistenceClass;

/**
 * Created by Burak Koken on 14.5.2018.
 *
 * @author Burak Koken
 */
public class ModelBinder {

    private Class annotatedClass;
    private Model modelAnnotation;
    private MetadataContext metadataContext;
    private PersistenceClass persistenceClass;

    public ModelBinder(Class annotatedClass, Model modelAnnotation, PersistenceClass persistenceClass,
                       MetadataContext metadataContext) {
        this.annotatedClass = annotatedClass;
        this.modelAnnotation = modelAnnotation;
        this.persistenceClass = persistenceClass;
        this.metadataContext = metadataContext;
    }

    /**
     * Bind the model.
     */
    public void bindModel() {

        if(BinderHelper.isEmptyAnnotationValue(modelAnnotation.value())) {
            persistenceClass.setAliasName(annotatedClass.getName());
        }
        else {
            persistenceClass.setAliasName(modelAnnotation.value());
        }
        persistenceClass.setModelName(annotatedClass.getName());
        persistenceClass.setClassName(annotatedClass.getName());


        String aliasName = persistenceClass.getAliasName();
        String modelName = persistenceClass.getModelName();
        metadataContext.getMetadataCollector().addModelAliasName(aliasName, modelName);

        if(!modelName.equals(aliasName)) {
            metadataContext.getMetadataCollector().addModelAliasName(modelName, modelName);
        }

    }

}
