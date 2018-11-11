package org.codnect.firesnap.core;

import android.util.Log;

import org.codnect.firesnap.annotation.Embeddable;
import org.codnect.firesnap.annotation.MappedSuperClass;
import org.codnect.firesnap.annotation.Model;
import org.codnect.firesnap.binder.AnnotationBinder;
import org.codnect.xreflect.ReflectionManager;
import org.codnect.xreflect.XClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Process the annotation metadata.
 *
 * Created by Burak Koken on 25.2.2018.
 *
 * @author Burak Koken
 */
public class AnnotationMetadataProcessor implements MetadataSourceProcessor {

    private static final String LOG_TAG = AnnotationMetadataProcessor.class.getSimpleName();

    private MetadataContext metadataContext;
    private ManagedResources managedResources;
    private List<XClass> categorizedClasses;
    private ReflectionManager reflectionManager;

    public AnnotationMetadataProcessor(MetadataContext metadataContext, ManagedResources managedResources) {
        this.metadataContext = metadataContext;
        this.managedResources = managedResources;
        categorizedClasses = new ArrayList<>();
        reflectionManager = metadataContext.getReflectionManager();
    }

    /**
     * Prepare the sources to create metadata from the annotation
     * metadata.
     */
    @Override
    public void prepare() {
        /* categorize all annotated classes */
        for(Class annotatedClass : managedResources.getAnnotatedClasses()) {
            categorizeAnnotatedClass(annotatedClass);
        }
    }

    /**
     * Process the annotations of the annotated classes.
     */
    @Override
    public void process() {
        List<XClass> allHierarchyClasses = getAllHierarchyClasses();
        Map<XClass, InheritanceState> inheritanceStateMap = AnnotationBinder.createInheritanceStates(
                allHierarchyClasses,
                metadataContext
        );
        /* bind the classes */
        for(XClass xClass : allHierarchyClasses) {
            AnnotationBinder.bindClass(xClass, inheritanceStateMap, metadataContext);
        }
    }

    /**
     * Finish step
     */
    @Override
    public void finish() {

    }

    /**
     * Categorize the specified annotated class.
     *
     * @param annotatedClass annotated class
     */
    private void categorizeAnnotatedClass(Class annotatedClass) {
        XClass xClass = reflectionManager.getXClass(annotatedClass);

        if (annotatedClass.isAnnotationPresent(Model.class)
                || annotatedClass.isAnnotationPresent(MappedSuperClass.class)
                || annotatedClass.isAnnotationPresent(Embeddable.class)) {
            categorizedClasses.add(xClass);
        } else {
            Log.w(LOG_TAG, "Encountered a non-categorized annotated class [" + annotatedClass.getName() + "]");
        }
    }

    /**
     *
     * @return
     */
    private List<XClass> getAllHierarchyClasses() {
        List<XClass> allHierarchyClasses = new ArrayList<>(categorizedClasses);
        for(XClass xClass : categorizedClasses) {
            XClass superClass = xClass.getSuperclass();
            while (superClass != null
                    && !reflectionManager.equals(superClass, Object.class)
                    && !allHierarchyClasses.contains(superClass)) {
                if(superClass.isAnnotationPresent(Model.class)
                        || superClass.isAnnotationPresent(MappedSuperClass.class)) {
                    allHierarchyClasses.add(superClass);
                }
                superClass = superClass.getSuperclass();
            }
        }

        List<XClass> copyAllHierarchyClasses = new ArrayList<>(allHierarchyClasses);
        List<XClass> orderedHierarchyClasses = new ArrayList<>(allHierarchyClasses.size());
        while (copyAllHierarchyClasses.size() > 0) {
            XClass xClass = copyAllHierarchyClasses.get(0);
            orderHierarchyClasses(allHierarchyClasses, copyAllHierarchyClasses, orderedHierarchyClasses, xClass);
        }

        return allHierarchyClasses;
    }

    /**
     *
     * @param allHierarchyClasses
     * @param copyAllHierarchyClasses
     * @param orderedHierarchyClasses
     * @param xClass
     */
    private void orderHierarchyClasses(List<XClass> allHierarchyClasses,
                                       List<XClass> copyAllHierarchyClasses,
                                       List<XClass> orderedHierarchyClasses,
                                       XClass xClass) {
        if(xClass == null || reflectionManager.equals(xClass, Object.class)) {
            return;
        }

        orderHierarchyClasses(allHierarchyClasses, copyAllHierarchyClasses, orderedHierarchyClasses, xClass.getSuperclass());
        if(allHierarchyClasses.contains(xClass)) {
            if(!orderedHierarchyClasses.contains(xClass)) {
                orderedHierarchyClasses.add(xClass);
            }
            copyAllHierarchyClasses.remove(xClass);
        }
    }

}
