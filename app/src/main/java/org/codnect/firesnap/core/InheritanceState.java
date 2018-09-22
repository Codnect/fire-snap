package org.codnect.firesnap.core;

import org.codnect.firesnap.annotation.AccessType;
import org.codnect.firesnap.annotation.Inheritance;
import org.codnect.firesnap.annotation.InheritanceStrategy;
import org.codnect.firesnap.annotation.MappedSuperClass;
import org.codnect.firesnap.reflection.ReflectionManager;
import org.codnect.firesnap.reflection.XClass;

import java.util.ArrayList;
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

    public InheritanceState(XClass xClass,
                            Map<XClass, InheritanceState> inheritanceStateMap,
                            MetadataContext metadataContext) {
        this.xClass = xClass;
        this.inheritanceStateMap = inheritanceStateMap;
        this.mappedSuperClasses = new ArrayList<>();
        this.metadataContext = metadataContext;
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
        ReflectionManager reflectionManager = metadataContext.getReflectionManager();
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

}
