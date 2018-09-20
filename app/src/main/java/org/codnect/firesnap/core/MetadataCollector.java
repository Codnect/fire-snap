package org.codnect.firesnap.core;

import org.codnect.firesnap.annotation.Embeddable;
import org.codnect.firesnap.annotation.MappedSuperClass;
import org.codnect.firesnap.annotation.Model;
import org.codnect.firesnap.exception.MappingException;
import org.codnect.firesnap.mapping.PersistentClass;
import org.codnect.firesnap.reflection.XClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public class MetadataCollector {

    private Map<String, String> modelAliasNames;
    private Map<String, PersistentClass> modelBindingMap;
    private Map<String, AnnotatedClassType> annotatedClassTypeMap;

    public MetadataCollector() {
        modelAliasNames = new HashMap<>();
        modelBindingMap = new HashMap<>();
        annotatedClassTypeMap = new HashMap<>();
    }

    /**
     * Add the alias names for model names.
     *
     * @param aliasName alias name for model name
     * @param modelName model name that will be added alias name
     */
    public void addModelAliasName(String aliasName, String modelName) {

        if(aliasName == null || modelName == null) {
            throw new IllegalArgumentException("Alias name or model name is null.");
        }

        modelAliasNames.put(aliasName, modelName);
    }

    /**
     * Add the persistence classes that is completed of the binding
     * process to model binding map.
     *
     * @param persistentClass persistence class
     */
    public void addModelBinding(PersistentClass persistentClass) {
        String modelName = persistentClass.getModelName();

        if(modelBindingMap.containsKey(modelName)) {
            throw new MappingException(modelName + " is duplicated.");
        }

        modelBindingMap.put(modelName, persistentClass);
    }

    /**
     *
     * @param modelName
     * @return
     */
    public PersistentClass getModelBinding(String modelName) {
        return modelBindingMap.get(modelName);
    }

    /**
     *
     * @param xClass
     * @return
     */
    public AnnotatedClassType getClassType(XClass xClass) {
        AnnotatedClassType annotatedClassType = annotatedClassTypeMap.get(xClass);
        if(annotatedClassType == null) {
            if(xClass.isAnnotationPresent(Model.class)) {
                annotatedClassType = AnnotatedClassType.MODEL;
            } else if(xClass.isAnnotationPresent(Embeddable.class)) {
                annotatedClassType = AnnotatedClassType.EMBEDDABLE;
            } else if(xClass.isAnnotationPresent(MappedSuperClass.class)) {
                annotatedClassType = AnnotatedClassType.EMBEDDABLE_SUPERCLASS;
            } else {
                annotatedClassType = AnnotatedClassType.NONE;
            }
            annotatedClassTypeMap.put(xClass.getName(), annotatedClassType);
        }
        return annotatedClassType;
    }

}
