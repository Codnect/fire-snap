package org.codnect.firesnap.core;

import org.codnect.firesnap.binder.ModelBinder;
import org.codnect.firesnap.inheritance.PersistentClass;
import org.codnect.firesnap.reflection.XClass;

import java.util.Map;

/**
 * Created by Burak Koken on 28.9.2018.
 *
 * @author Burak Koken
 */
public class PropertyHolderFactory {

    /**
     *
     * @param persistentClass
     * @param modelBinder
     * @param inheritanceStateMap
     * @param metadataContext
     * @return
     */
    public static PropertyHolder createClassPropertyHolder(PersistentClass persistentClass,
                                                           ModelBinder modelBinder,
                                                           Map<XClass, InheritanceState> inheritanceStateMap,
                                                           MetadataContext metadataContext) {
        return new ClassPropertyHolder(persistentClass, modelBinder, inheritanceStateMap, metadataContext);
    }

}
