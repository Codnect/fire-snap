package org.codnect.firesnap.binder;

import org.codnect.firesnap.annotation.AccessType;
import org.codnect.firesnap.core.InheritanceState;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.core.PropertyHolder;
import org.codnect.firesnap.mapping.NodeProperty;
import org.codnect.firesnap.mapping.SimpleValue;
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
    private boolean isIdProperty;
    private SimpleValue value;
    private SimpleValueBinder simpleValueBinder;
    private MetadataContext metadataContext;

    public PropertyBinder(MetadataContext metadataContext) {
        this.metadataContext = metadataContext;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public void setDeclaringClass(XClass declaringClass) {
        this.declaringClass = declaringClass;
    }

    public void setModelBinder(ModelBinder modelBinder) {
        this.modelBinder = modelBinder;
    }

    public void setPropertyHolder(PropertyHolder propertyHolder) {
        this.propertyHolder = propertyHolder;
    }

    public void setInheritanceStateMap(Map<XClass, InheritanceState> inheritanceStateMap) {
        this.inheritanceStateMap = inheritanceStateMap;
    }

    public void setMetadataContext(MetadataContext metadataContext) {
        this.metadataContext = metadataContext;
    }

    public void setIdProperty(boolean idProperty) {
        isIdProperty = idProperty;
    }

    public SimpleValue getValue() {
        return value;
    }

    public NodeProperty bind() {
        simpleValueBinder = new SimpleValueBinder(metadataContext);
        simpleValueBinder.setPropertyName(propertyName);
        simpleValueBinder.setAccessType(accessType);
        simpleValueBinder.setPersistentClassName(propertyHolder.getClassName());
        value = simpleValueBinder.bind();
        return null;
    }

}
