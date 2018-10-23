package org.codnect.firesnap.binder;

import android.util.Log;

import org.codnect.firesnap.annotation.Discriminator;
import org.codnect.firesnap.annotation.DiscriminatorValue;
import org.codnect.firesnap.annotation.Id;
import org.codnect.firesnap.annotation.InheritanceStrategy;
import org.codnect.firesnap.annotation.ManyToOne;
import org.codnect.firesnap.annotation.MappedSuperClass;
import org.codnect.firesnap.annotation.Model;
import org.codnect.firesnap.annotation.Node;
import org.codnect.firesnap.annotation.OneToOne;
import org.codnect.firesnap.annotation.Property;
import org.codnect.firesnap.core.AnnotatedClassType;
import org.codnect.firesnap.core.InheritanceState;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.core.ModelNodeReference;
import org.codnect.firesnap.exception.AnnotationException;
import org.codnect.firesnap.exception.MappingException;
import org.codnect.firesnap.exception.PersistenceException;
import org.codnect.firesnap.mapping.DiscriminatorNodeProperty;
import org.codnect.firesnap.inheritance.JoinedSubclass;
import org.codnect.firesnap.inheritance.PersistentClass;
import org.codnect.firesnap.mapping.NodeJoinProperty;
import org.codnect.firesnap.mapping.NodePropertyBuilder;
import org.codnect.firesnap.mapping.OneToOneSecondStep;
import org.codnect.firesnap.mapping.PropertyData;
import org.codnect.firesnap.mapping.PropertyDataCollector;
import org.codnect.firesnap.core.PropertyHolder;
import org.codnect.firesnap.core.PropertyHolderFactory;
import org.codnect.firesnap.inheritance.RootClass;
import org.codnect.firesnap.inheritance.SingleNodeSubclass;
import org.codnect.firesnap.inheritance.UnionSubclass;
import org.codnect.firesnap.reflection.XClass;
import org.codnect.firesnap.reflection.XProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Burak Koken on 14.5.2018.
 *
 * @author Burak Koken
 */
public class AnnotationBinder {

    private static final String LOG_TAG = AnnotationBinder.class.getSimpleName();

    /**
     * Bind the annotated class.
     *
     * @param xClass annotated class
     */
    public static void bindClass(XClass xClass,
                                 Map<XClass,InheritanceState> inheritanceStateMap,
                                 MetadataContext metadataContext) {

        if(xClass.isAnnotationPresent(Model.class) && xClass.isAnnotationPresent(MappedSuperClass.class)) {
            throw new AnnotationException("A model cannot has both @Model and @MappedSuperClass" + xClass.getName());
        }

        /* get the class's inheritance state and type */
        InheritanceState inheritanceState = inheritanceStateMap.get(xClass);
        AnnotatedClassType annotatedClassType = metadataContext.getMetadataCollector().getClassType(xClass);

        /* if class type is a model */
        if(BinderHelper.isModelClassType(annotatedClassType, xClass)) {
            /* if class has an super model, get its persistent class  */
            PersistentClass superModelPersistentClass = getSuperModelPersistentClass(xClass,
                    inheritanceStateMap,
                    inheritanceState,
                    metadataContext);
            /* create a persistent class for class */
            PersistentClass persistentClass = createPersistentClass(inheritanceState, superModelPersistentClass, metadataContext);
            /* create a model binder to bind the model */
            Model modelAnnotation = xClass.getAnnotation(Model.class);
            ModelBinder modelBinder = new ModelBinder(xClass, modelAnnotation, persistentClass, metadataContext);
            modelBinder.setInheritanceState(inheritanceState);
            /* bind the model */
            modelBinder.bindModel();
            /* process discriminator property */
            DiscriminatorNodeProperty discriminatorNodeProperty = processDiscriminatorProperty(
                    xClass,
                    inheritanceState,
                    modelBinder,
                    metadataContext);
            /* bind the node */
            if(inheritanceState.hasNode()) {
                String nodeName = "";
                Node nodeAnnotation = xClass.getAnnotation(Node.class);
                if(nodeAnnotation != null) {
                    nodeName = nodeAnnotation.name();
                }
                ModelNodeReference superModelNodeReference = null;
                if(inheritanceState.hasDenormalizedNode()) {
                    superModelNodeReference = metadataContext.getMetadataCollector().getModelNodeReference(
                            superModelPersistentClass.getModelName()
                    );
                }
                modelBinder.bindNode(nodeName, superModelNodeReference);
            } else {
                if(xClass.isAnnotationPresent(Node.class)) {
                    Log.w(LOG_TAG, "Node annotation is ignored for subclasses that have single node strategy");
                }
                if(inheritanceState.getStrategy() ==  InheritanceStrategy.SINGLE_NODE) {
                    modelBinder.bindNodeForSingleNodeStrategySubClass(
                            metadataContext.getMetadataCollector().getModelNodeReference(superModelPersistentClass.getModelName())
                    );
                }
            }
            /* get all properties */
            PropertyDataCollector propertyDataCollector = inheritanceState.getPropertyDataCollector();
            /* create a property holder */
            PropertyHolder propertyHolder = PropertyHolderFactory.createClassPropertyHolder(
                    persistentClass,
                    modelBinder,
                    inheritanceStateMap,
                    metadataContext
            );
            /* process the property annotations */
            processPropertyAnnotations(propertyDataCollector, propertyHolder, persistentClass, modelBinder, inheritanceStateMap, metadataContext);
            /* add to metadata collector */
            metadataContext.getMetadataCollector().addModelBinding(persistentClass);
        }

    }

    /**
     *
     * @param classes
     * @param metadataContext
     * @return
     */
    public static Map<XClass, InheritanceState> createInheritanceStates(List<XClass> classes,
                                                                 MetadataContext metadataContext) {
        Map<XClass, InheritanceState> inheritanceStateMap = new HashMap<>(classes.size());
        for(XClass xClass : classes) {
            InheritanceState classInheritanceState = new InheritanceState(xClass, inheritanceStateMap, metadataContext);
            InheritanceState superClassInheritanceState = InheritanceState
                    .getSuperClassInheritanceState(xClass, inheritanceStateMap);

            if(superClassInheritanceState != null) {
                superClassInheritanceState.setHasSiblings(true);
                InheritanceState superModelInheritanceState = InheritanceState
                        .getSuperModelInheritanceState(xClass, inheritanceStateMap);
                if(superModelInheritanceState != null) {
                    classInheritanceState.setHasParents(true);
                }

                if(superClassInheritanceState.getStrategy() != null) {
                    boolean isMixedStrategy = false;
                    if(classInheritanceState.getStrategy() != null
                            && !classInheritanceState.equals(InheritanceStrategy.SINGLE_NODE)
                            && !classInheritanceState.getStrategy().equals(superClassInheritanceState.getStrategy())) {
                        isMixedStrategy = true;
                    }
                    if(isMixedStrategy) {
                        throw new AnnotationException("Invalid inheritance strategy for " + xClass.getName());
                    }
                    classInheritanceState.setStrategy(superClassInheritanceState.getStrategy());
                }
            }
            inheritanceStateMap.put(xClass, classInheritanceState);
        }
        return inheritanceStateMap;
    }

    /**
     *
     * @param xClass
     * @param inheritanceStateMap
     * @param inheritanceState
     * @param metadataContext
     * @return
     */
    private static PersistentClass getSuperModelPersistentClass(XClass xClass,
                                                               Map<XClass, InheritanceState> inheritanceStateMap,
                                                               InheritanceState inheritanceState,
                                                               MetadataContext metadataContext) {
        InheritanceState superModelInheritanceState = InheritanceState.getSuperModelInheritanceState(
                xClass,
                inheritanceStateMap
        );
        PersistentClass superModelPersistentClass = null;
        if(superModelInheritanceState != null) {
            superModelPersistentClass = metadataContext.getMetadataCollector().getModelBinding(
                    superModelInheritanceState.getXClass().getName()
            );
            if(superModelPersistentClass == null && inheritanceState.hasParents()) {
                throw new MappingException("Subclasses have to be mapped after their parents : "
                        + superModelInheritanceState.getXClass().getName());
            }
        }
        return superModelPersistentClass;
    }

    /**
     *
     * @param inheritanceState
     * @param superModelPersistentClass
     * @param metadataContext
     * @return
     */
    private static PersistentClass createPersistentClass(InheritanceState inheritanceState,
                                                         PersistentClass superModelPersistentClass,
                                                         MetadataContext metadataContext) {
        /* if class has no any parents */
        if(!inheritanceState.hasParents()) {
            return new RootClass(metadataContext);
        } else if(inheritanceState.getStrategy() == InheritanceStrategy.SINGLE_NODE) {
            return new SingleNodeSubclass(metadataContext, superModelPersistentClass);
        } else if(inheritanceState.getStrategy() == InheritanceStrategy.NODE_PER_CLASS) {
            return new UnionSubclass(metadataContext, superModelPersistentClass);
        } else if(inheritanceState.getStrategy() == InheritanceStrategy.JOINED) {
            return new JoinedSubclass(metadataContext, superModelPersistentClass);
        }

        throw new PersistenceException("Unknown inheritance strategy : " + inheritanceState.getStrategy());
    }

    /**
     *
     * @param xClass
     * @param inheritanceState
     * @param modelBinder
     * @param metadataContext
     * @return
     */
    private static DiscriminatorNodeProperty processDiscriminatorProperty(XClass xClass,
                                                                          InheritanceState inheritanceState,
                                                                          ModelBinder modelBinder,
                                                                          MetadataContext metadataContext) {
        DiscriminatorNodeProperty discriminatorProperty = null;
        Discriminator discriminatorAnnotation = xClass.getAnnotation(Discriminator.class);
        if(inheritanceState.getStrategy() == InheritanceStrategy.SINGLE_NODE || inheritanceState.getStrategy() == InheritanceStrategy.JOINED) {
            if(!inheritanceState.hasParents()) {
                discriminatorProperty = DiscriminatorNodeProperty.createDiscriminatorProperty(discriminatorAnnotation, metadataContext);
            }
            else if(discriminatorAnnotation != null && !inheritanceState.hasParents()) {
                Log.w(LOG_TAG, "Discriminator annotation can only be used in the root model, it is ignored for subclass : "
                        + xClass.getName());
            }
            String discriminatorValue = null;
            if(xClass.isAnnotationPresent(DiscriminatorValue.class)) {
                discriminatorValue = xClass.getAnnotation(DiscriminatorValue.class).value();
            }
            modelBinder.setDiscriminatorValue(discriminatorValue);
        }
        return discriminatorProperty;
    }

    /**
     *
     * @param propertyDataCollector
     * @param persistentClass
     * @param modelBinder
     * @param inheritanceStateMap
     * @param metadataContext
     */
    private static void processPropertyAnnotations(PropertyDataCollector propertyDataCollector,
                                                   PropertyHolder propertyHolder,
                                                   PersistentClass persistentClass,
                                                   ModelBinder modelBinder,
                                                   Map<XClass, InheritanceState> inheritanceStateMap,
                                                   MetadataContext metadataContext) {
        for(PropertyData propertyData : propertyDataCollector.getPropertyDataList()) {
            PropertyBinder propertyBinder = new PropertyBinder(metadataContext);
            propertyBinder.setPropertyName(propertyData.getPropertyName());
            propertyBinder.setAccessType(propertyData.getDefaultAccess());
            propertyBinder.setDeclaringClass(propertyData.getDeclaringClass());
            propertyBinder.setInheritanceStateMap(inheritanceStateMap);
            propertyBinder.setModelBinder(modelBinder);
            propertyBinder.setPropertyHolder(propertyHolder);
            XProperty property = propertyData.getProperty();
            if(property.isAnnotationPresent(Id.class)) {
                propertyBinder.setIdProperty(true);
            }

            NodePropertyBuilder nodePropertyBuilder = new NodePropertyBuilder(
                    property,
                    propertyData,
                    propertyHolder,
                    modelBinder,
                    metadataContext);
            nodePropertyBuilder.build();
            NodeJoinProperty nodeJoinProperty = nodePropertyBuilder.getNodeJoinProperty();

            if(property.isAnnotationPresent(OneToOne.class)) {
                if(property.isAnnotationPresent(Property.class)) {
                    throw new AnnotationException("Property annotation not allowed for @OneToOne property");
                }
                OneToOne oneToOneAnnotation = property.getAnnotation(OneToOne.class);
                bindOneToOne(
                        propertyBinder,
                        propertyData,
                        propertyHolder,
                        nodeJoinProperty,
                        oneToOneAnnotation,
                        metadataContext);
            } else if(property.isAnnotationPresent(ManyToOne.class)) {
                if(property.isAnnotationPresent(Property.class)) {
                    throw new AnnotationException("Property annotation not allowed for @ManyToOne property");
                }
            }
        }
    }

    /**
     *
     * @param propertyBinder
     * @param propertyData
     * @param propertyHolder
     * @param nodeJoinProperty
     * @param oneToOneAnnotation
     * @param metadataContext
     */
    private static void bindOneToOne(PropertyBinder propertyBinder,
                                     PropertyData propertyData,
                                     PropertyHolder propertyHolder,
                                     NodeJoinProperty nodeJoinProperty,
                                     OneToOne oneToOneAnnotation,
                                     MetadataContext metadataContext) {
        if(!BinderHelper.isEmptyAnnotationValue(oneToOneAnnotation.mappedBy())) {
            OneToOneSecondStep oneToOneSecondStep = new OneToOneSecondStep(
                    propertyData,
                    propertyHolder,
                    oneToOneAnnotation.mappedBy(),
                    nodeJoinProperty,
                    metadataContext
            );
            metadataContext.getMetadataCollector().addSecondStep(oneToOneSecondStep);
        } else {
           bindManyToOne(propertyBinder, propertyData, propertyHolder, nodeJoinProperty, true, metadataContext);
        }
    }

    /**
     *
     * @param propertyBinder
     * @param propertyData
     * @param propertyHolder
     * @param nodeJoinProperty
     * @param isLogicalOneToOne
     * @param metadataContext
     */
    private static void bindManyToOne(PropertyBinder propertyBinder,
                                      PropertyData propertyData,
                                      PropertyHolder propertyHolder,
                                      NodeJoinProperty nodeJoinProperty,
                                      boolean isLogicalOneToOne,
                                      MetadataContext metadataContext) {
        org.codnect.firesnap.mapping.ManyToOne manyToOne = new org.codnect.firesnap.mapping.ManyToOne(metadataContext);
        manyToOne.setLogicalOneToOne(isLogicalOneToOne);
        XProperty property = propertyData.getProperty();
        String propertyName = propertyData.getPropertyName();
        String propertyPath = propertyHolder.getPath() + propertyName;
    }

}
