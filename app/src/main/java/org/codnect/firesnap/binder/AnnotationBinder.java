package org.codnect.firesnap.binder;

import org.codnect.firesnap.annotation.InheritanceStrategy;
import org.codnect.firesnap.annotation.MappedSuperClass;
import org.codnect.firesnap.annotation.Model;
import org.codnect.firesnap.core.AnnotatedClassType;
import org.codnect.firesnap.core.InheritanceState;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.exception.AnnotationException;
import org.codnect.firesnap.mapping.PersistenceClass;
import org.codnect.firesnap.reflection.XClass;

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

        InheritanceState inheritanceState = inheritanceStateMap.get(xClass);
        AnnotatedClassType annotatedClassType = metadataContext.getMetadataCollector().getClassType(xClass);

        if(isModelClassType(annotatedClassType, xClass)) {
            PersistenceClass persistenceClass = new PersistenceClass();
            Model modelAnnotation = xClass.getAnnotation(Model.class);
            ModelBinder modelBinder = new ModelBinder(xClass, modelAnnotation, persistenceClass, metadataContext);
            modelBinder.bindModel();

            /* ... */

            metadataContext.getMetadataCollector().addModelBinding(persistenceClass);
        }

    }

    /**
     * Get the if the annotated class is a Model class.
     *
     * @param xClass annotated class
     * @return if the annotated class is a Model class, it returns
     *         true. Otherwise it returns false.
     */
    public static boolean isModelClassType(AnnotatedClassType annotatedClassType, XClass xClass) {
        if(annotatedClassType == AnnotatedClassType.NONE
                || annotatedClassType == AnnotatedClassType.EMBEDDABLE
                || annotatedClassType == AnnotatedClassType.EMBEDDABLE_SUPERCLASS) {
            return false;
        }

        if(!xClass.isAnnotationPresent(Model.class)) {
            throw new AnnotationException("Annotated class should have only a @Model, @Embeddable or @MappedSuperClass: " +
                    xClass.getName());
        }
        return true;
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

}
