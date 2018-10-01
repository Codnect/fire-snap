package org.codnect.firesnap.core;

import org.codnect.firesnap.annotation.Access;
import org.codnect.firesnap.annotation.AccessType;
import org.codnect.firesnap.annotation.Id;
import org.codnect.firesnap.annotation.Inheritance;
import org.codnect.firesnap.annotation.InheritanceStrategy;
import org.codnect.firesnap.annotation.MappedSuperClass;
import org.codnect.firesnap.annotation.Model;
import org.codnect.firesnap.exception.AnnotationException;
import org.codnect.firesnap.exception.MappingException;
import org.codnect.firesnap.mapping.SimplePropertyData;
import org.codnect.firesnap.mapping.PropertyContainer;
import org.codnect.firesnap.mapping.PropertyData;
import org.codnect.firesnap.mapping.PropertyDataCollector;
import org.codnect.firesnap.reflection.ReflectionManager;
import org.codnect.firesnap.reflection.XClass;
import org.codnect.firesnap.reflection.XProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Burak Koken on 18.9.2018.
 *
 * @author Burak Koken
 */
public class InheritanceState {

    private XClass xClass;
    private Map<XClass, InheritanceState> inheritanceStateMap;
    private boolean hasParents;
    private boolean hasSiblings;
    private InheritanceStrategy strategy;
    private AccessType accessType;
    private boolean isEmbeddableSuperclass;
    private List<XClass> mappedSuperClasses;
    private MetadataContext metadataContext;
    private ReflectionManager reflectionManager;
    private PropertyDataCollector propertyDataCollector;

    public InheritanceState(XClass xClass,
                            Map<XClass, InheritanceState> inheritanceStateMap,
                            MetadataContext metadataContext) {
        this.xClass = xClass;
        this.inheritanceStateMap = inheritanceStateMap;
        this.mappedSuperClasses = new ArrayList<>();
        this.metadataContext = metadataContext;
        this.reflectionManager = metadataContext.getReflectionManager();
        determineInheritanceStrategy();
    }

    public XClass getXClass() {
        return xClass;
    }

    public void setXClass(XClass xClass) {
        this.xClass = xClass;
    }

    public boolean hasParents() {
        return hasParents;
    }

    public void setHasParents(boolean hasParents) {
        this.hasParents = hasParents;
    }

    public boolean hasSiblings() {
        return hasSiblings;
    }

    public void setHasSiblings(boolean hasSiblings) {
        this.hasSiblings = hasSiblings;
    }

    public InheritanceStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(InheritanceStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean isEmbeddableSuperclass() {
        return isEmbeddableSuperclass;
    }

    public void setEmbeddableSuperclass(boolean embeddableSuperclass) {
        isEmbeddableSuperclass = embeddableSuperclass;
    }

    public boolean hasNode() {
        return !hasParents || strategy != InheritanceStrategy.SINGLE_NODE;
    }

    public boolean hasDenormalizedNode() {
        return hasParents() && strategy == InheritanceStrategy.NODE_PER_CLASS;
    }

    /**
     *
     * @return
     */
    private void determineInheritanceStrategy() {
        Inheritance inheritanceAnnotation = xClass.getAnnotation(Inheritance.class);
        MappedSuperClass mappedSuperClassAnnotation = xClass.getAnnotation(MappedSuperClass.class);
        if(mappedSuperClassAnnotation != null) {
            isEmbeddableSuperclass = true;
            if(inheritanceAnnotation != null) {
                strategy = inheritanceAnnotation.strategy();
            } else {
                strategy = null;
            }
        } else {
            if(inheritanceAnnotation != null) {
                strategy = inheritanceAnnotation.strategy();
            } else {
                strategy = InheritanceStrategy.SINGLE_NODE;
            }
        }
    }

    /**
     *
     * @return
     */
    private AccessType getDefaultAccessType() {
        for (XClass xClass = this.xClass; xClass != null; xClass = xClass.getSuperclass()) {
            if((xClass.isAnnotationPresent(Model.class) || xClass.isAnnotationPresent(MappedSuperClass.class))
                    && (xClass.getSuperclass() == null || reflectionManager.equals(xClass.getSuperclass(), Object.class))
                    && (xClass.isAnnotationPresent(Access.class))) {
                return xClass.getAnnotation(Access.class).value();
            }
        }
        return determineAccessTypeAccordingToProperties();
    }

    /**
     *
     * @return
     */
    private AccessType determineAccessTypeAccordingToProperties()   {
        for(XClass xClass = this.xClass; xClass != null; xClass = xClass.getSuperclass()) {
            if((xClass.isAnnotationPresent(Model.class) || xClass.isAnnotationPresent(MappedSuperClass.class))
                    && (xClass.getSuperclass() == null || reflectionManager.equals(xClass.getSuperclass(), Object.class))){
                for(XProperty xProperty : xClass.getDeclaredMethodProperties()) {
                    if(xProperty.isAnnotationPresent(Id.class) /* || xProperty.isAnnotationPresent(EmbeddedId.class) */) {
                        return AccessType.METHOD;
                    }
                }
                for(XProperty xProperty : xClass.getDeclaredFieldProperties()) {
                    if(xProperty.isAnnotationPresent(Id.class) /* || xProperty.isAnnotationPresent(EmbeddedId.class) */) {
                        return AccessType.FIELD;
                    }
                }
            }
        }
        throw new AnnotationException("Model has no identifier : " + xClass.getName());
    }

    /**
     *
     * @param xClass
     * @param inheritanceStateMap
     * @return
     */
    public static InheritanceState getSuperClassInheritanceState(XClass xClass,
                                                                 Map<XClass, InheritanceState> inheritanceStateMap) {
        XClass superClass = xClass.getSuperclass();
        while (superClass != null && !superClass.getName().equals(Object.class.getName())) {
            InheritanceState superClassState = inheritanceStateMap.get(superClass);
            if (superClassState != null) {
                return superClassState;
            }
            superClass = superClass.getSuperclass();
        }
        return null;
    }

    /**
     *
     *
     * @param xClass
     * @param inheritanceStateMap
     * @return
     */
    public static InheritanceState getSuperModelInheritanceState(XClass xClass,
                                                                 Map<XClass, InheritanceState> inheritanceStateMap) {
        XClass superClass = xClass.getSuperclass();
        while (superClass != null && !superClass.getName().equals(Object.class.getName())) {
            InheritanceState superClassState = inheritanceStateMap.get(superClass);
            if (superClassState != null && !superClassState.isEmbeddableSuperclass()) {
                return superClassState;
            }
            superClass = superClass.getSuperclass();
        }
        return null;
    }

    /**
     * Get the mapped super classes for this element's class.
     */
    private List<XClass> getMappedSuperClasses()  {
        if(mappedSuperClasses.size() != 0) {
            return mappedSuperClasses;
        }

        XClass currentClass = xClass;
        InheritanceState superClassInheritanceState;
        while (true) {
            mappedSuperClasses.add(0, currentClass);
            XClass superClass = currentClass;
            while (true) {
                superClass = superClass.getSuperclass();
                superClassInheritanceState = inheritanceStateMap.get(superClass);
                if(superClass == null || superClassInheritanceState != null || reflectionManager.equals(superClass, Object.class)) {
                    break;
                }
            }
            currentClass = superClass;
            if(superClassInheritanceState == null || !superClassInheritanceState.isEmbeddableSuperclass()) {
                break;
            }
        }

        return mappedSuperClasses;
    }

    /**
     *
     */
    public PropertyDataCollector getPropertyDataCollector() {
        if(propertyDataCollector == null) {
            InheritanceState inheritanceState = inheritanceStateMap.get(xClass);
            if(inheritanceState.isEmbeddableSuperclass()) {
                throw new MappingException("You cannot get the properties data for mapped super class: " + xClass.getName());
            }
            accessType = getDefaultAccessType();
            getMappedSuperClasses();
            propertyDataCollector = new PropertyDataCollector();
            for(XClass xClass : mappedSuperClasses) {
                PropertyContainer propertyContainer = new PropertyContainer(xClass, this.xClass, accessType);
                propertyContainer.processProperties();
                addPropertiesOfClass(propertyContainer);
            }
        }
        return propertyDataCollector;
    }

    /**
     *
     * @param propertyContainer
     */
    private void addPropertiesOfClass(PropertyContainer propertyContainer) {
        Collection<XProperty> properties = propertyContainer.getProperties();
        for(XProperty xProperty : properties) {
            for(PropertyData propertyData : propertyDataCollector.getPropertyDataList()) {
                if(propertyData.getPropertyName().equals(xProperty.getName())) {
                    return;
                }
            }
            XClass declaringClass = propertyContainer.getDeclaringClass();
            AccessType defaultAccessType = propertyContainer.getClassLevelAccessType();
            PropertyData propertyData = new SimplePropertyData(xProperty, defaultAccessType, declaringClass);
            propertyDataCollector.addPropertyData(propertyData);
        }
    }

}
