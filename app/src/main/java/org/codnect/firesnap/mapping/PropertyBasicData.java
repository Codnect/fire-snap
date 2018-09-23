package org.codnect.firesnap.mapping;

import org.codnect.firesnap.annotation.Access;
import org.codnect.firesnap.annotation.AccessType;
import org.codnect.firesnap.reflection.XClass;
import org.codnect.firesnap.reflection.XProperty;

/**
 * Created by Burak Koken on 23.9.2018.
 *
 * @author Burak Koken
 */
public class PropertyBasicData implements PropertyData{

    private XProperty xProperty;
    private AccessType defaulAccessType;
    private XClass declaringClass;

    public PropertyBasicData(XProperty xProperty,
                             AccessType defaultAccessType,
                             XClass declaringClass) {
        this.xProperty = xProperty;
        this.defaulAccessType = defaultAccessType;
        this.declaringClass = declaringClass;
    }

    @Override
    public String getPropertyName() {
        return xProperty.getName();
    }

    @Override
    public XProperty getProperty() {
        return xProperty;
    }

    @Override
    public XClass getPropertyClass() {
        return xProperty.getType();
    }

    @Override
    public String getTypeName() {
        return xProperty.getType().getName();
    }

    @Override
    public AccessType getDefaultAccess() {
        AccessType accessType = defaulAccessType;
        if(xProperty.isAnnotationPresent(Access.class)) {
            accessType = xProperty.getAnnotation(Access.class).value();
        }
        return accessType;
    }

    @Override
    public String getClassOrElementName() {
        return xProperty.getClassOrElementClass().getName();
    }

    @Override
    public XClass getClassOrElement() {
        return xProperty.getClassOrElementClass();
    }

    @Override
    public XClass getDeclaringClass() {
        return declaringClass;
    }

}
