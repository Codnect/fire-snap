package org.codnect.firesnap.mapping;

import org.codnect.firesnap.annotation.Access;
import org.codnect.firesnap.annotation.AccessType;
import org.codnect.firesnap.annotation.Transient;
import org.codnect.firesnap.exception.MappingException;
import org.codnect.firesnap.reflection.XClass;
import org.codnect.firesnap.reflection.XProperty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public class PropertyContainer {

    private XClass xClass;
    private XClass modelClass;
    private Map<String, XProperty> propertyMap;
    private AccessType classLevelAccessType;

    public PropertyContainer(XClass xClass, XClass modelClass, AccessType defaultAccessType) {
        this.xClass = xClass;
        this.modelClass = modelClass;
        propertyMap = new TreeMap<>();
        if(defaultAccessType == AccessType.DEFAULT) {
            classLevelAccessType = AccessType.METHOD;
        }
        AccessType accessType = determineClassLevelAccessType();
        if(accessType == AccessType.DEFAULT) {
            classLevelAccessType = defaultAccessType;
        } else {
            classLevelAccessType = accessType;
        }
    }

    /**
     *
     * @return
     */
    public XClass getDeclaringClass() {
        return xClass;
    }

    /**
     *
     * @return
     */
    public XClass getModelClass() {
        return modelClass;
    }

    /**
     *
     * @return
     */
    public AccessType getClassLevelAccessType() {
        return classLevelAccessType;
    }

    /**
     *
     * @return
     */
    public Collection<XProperty> getProperties() {
        return propertyMap.values();
    }

    /**
     *
     */
    private AccessType determineClassLevelAccessType() {
        AccessType accessType = AccessType.DEFAULT;
        if(xClass.isAnnotationPresent(Access.class)) {
            accessType = xClass.getAnnotation(Access.class).value();
        }
        return accessType;
    }

    /**
     *
     */
    public void processProperties() {
        /* get all properties */
        List<XProperty> fieldProperties = xClass.getDeclaredFieldProperties();
        List<XProperty> methodProperties = xClass.getDeclaredMethodProperties();
        /* filter the properties */
        filterProperties(fieldProperties);
        filterProperties(methodProperties);
        /* collect the properties */
        collectProperties(fieldProperties, methodProperties);
    }

    /***
     *
     * @param properties
     */
    public void filterProperties(List<XProperty> properties) {
        Iterator<XProperty> propertyIterator = properties.iterator();
        while(propertyIterator.hasNext()) {
            XProperty xProperty = propertyIterator.next();
            if(xProperty.isAnnotationPresent(Transient.class)) {
                properties.remove(xProperty);
            }
        }
    }

    /**
     *
     * @param fieldProperties
     * @param methodProperties
     */
    private void collectProperties(List<XProperty> fieldProperties, List<XProperty> methodProperties) {
        Map<String, XProperty> visitedMethodPropertyMap = new HashMap<>();
        collectPropertiesUsingLocalAccessType(visitedMethodPropertyMap, fieldProperties, methodProperties);
        collectPropertiesUsingClassLevelAccessType(visitedMethodPropertyMap, fieldProperties, methodProperties);
    }

    /**
     *
     * @param visitedMethodPropertyMap
     * @param fieldProperties
     * @param methodProperties
     */
    private void collectPropertiesUsingLocalAccessType(Map<String, XProperty> visitedMethodPropertyMap,
                                                       List<XProperty> fieldProperties,
                                                       List<XProperty> methodProperties) {
        Iterator<XProperty> fieldPropertyIterator = fieldProperties.iterator();
        while(fieldPropertyIterator.hasNext()) {
            XProperty fieldProperty = fieldPropertyIterator.next();
            if(fieldProperty.isAnnotationPresent(Access.class)) {
                AccessType localAccessType = fieldProperty.getAnnotation(Access.class).value();
                if(localAccessType != AccessType.FIELD) {
                    continue;
                }
            }
            fieldProperties.remove(fieldProperty);
            propertyMap.put(fieldProperty.getName(), fieldProperty);
        }
        Iterator<XProperty> methodPropertyIterator = methodProperties.iterator();
        while(methodPropertyIterator.hasNext()) {
            XProperty methodProperty = methodPropertyIterator.next();
            if(methodProperty.isAnnotationPresent(Access.class)) {
                AccessType localAccessType = methodProperty.getAnnotation(Access.class).value();
                if(localAccessType != AccessType.METHOD) {
                    continue;
                }
            }
            methodProperties.remove(methodProperty);
            String methodName = methodProperty.getName();
            if(visitedMethodPropertyMap.get(methodName) != null) {
                throw new MappingException("The method must not be overloaded : " + methodProperty.getName());
            }
            propertyMap.put(methodName, methodProperty);
            visitedMethodPropertyMap.put(methodName, methodProperty);
        }
    }

    /**
     *
     * @param visitedMethodPropertyMap
     * @param fieldProperties
     * @param methodProperties
     */
    private void collectPropertiesUsingClassLevelAccessType(Map<String, XProperty> visitedMethodPropertyMap,
                                                            List<XProperty> fieldProperties,
                                                            List<XProperty> methodProperties) {
        if(classLevelAccessType == AccessType.FIELD) {
            for(XProperty fieldProperty : fieldProperties) {
                if (propertyMap.containsKey(fieldProperty.getName())) {
                    continue;
                }
                propertyMap.put(fieldProperty.getName(), fieldProperty);
            }
        } else {
            for(XProperty methodProperty : methodProperties) {
                String methodName = methodProperty.getName();
                if(visitedMethodPropertyMap.get(methodName) != null) {
                    throw new MappingException("The method cannot not be overloaded : " + methodProperty.getName());
                }
                if (propertyMap.containsKey(methodName)) {
                    continue;
                }
                propertyMap.put(methodName, methodProperty);
                visitedMethodPropertyMap.put(methodName, methodProperty);
            }
        }
    }

}
