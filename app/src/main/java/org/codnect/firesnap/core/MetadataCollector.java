package org.codnect.firesnap.core;

import org.codnect.firesnap.exception.MappingException;
import org.codnect.firesnap.mapping.PersistenceClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public class MetadataCollector {

    private Map<String, String> modelAliasNames;
    private Map<String, PersistenceClass> modelBindingMap;

    public MetadataCollector() {
        modelAliasNames = new HashMap<>();
        modelBindingMap = new HashMap<>();
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
     * @param persistenceClass persistence class
     */
    public void addModelBinding(PersistenceClass persistenceClass) {
        String modelName = persistenceClass.getModelName();

        if(modelBindingMap.containsKey(modelName)) {
            throw new MappingException(modelName + " is duplicated.");
        }

        modelBindingMap.put(modelName, persistenceClass);
    }

}
