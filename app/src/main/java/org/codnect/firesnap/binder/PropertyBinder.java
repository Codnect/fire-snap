package org.codnect.firesnap.binder;

import org.codnect.firesnap.annotation.AccessType;
import org.codnect.firesnap.core.InheritanceState;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.core.PropertyHolder;
import org.codnect.firesnap.reflection.XClass;

import java.util.Map;

/**
 * Created by Burak Koken on 30.9.2018.
 *
 * @author Burak Koken
 */
public class PropertyBinder {

    private String propertyName;
    private AccessType accessType;
    private XClass declaringClass;
    private ModelBinder modelBinder;
    private PropertyHolder propertyHolder;
    private Map<XClass, InheritanceState> inheritanceStateMap;
    private MetadataContext metadataContext;

    public PropertyBinder(String propertyName,
                          AccessType accessType,
                          XClass declaringClass,
                          ModelBinder modelBinder,
                          PropertyHolder propertyHolder,
                          Map<XClass, InheritanceState> inheritanceStateMap,
                          MetadataContext metadataContext) {
        this.propertyName = propertyName;
        this.accessType = accessType;
        this.declaringClass = declaringClass;
        this.modelBinder = modelBinder;
        this.propertyHolder = propertyHolder;
        this.inheritanceStateMap = inheritanceStateMap;
        this.metadataContext = metadataContext;
    }

}
