package org.codnect.firesnap.binder;

import org.codnect.firesnap.annotation.Embeddable;
import org.codnect.firesnap.annotation.Model;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.exception.AnnotationException;
import org.codnect.firesnap.mapping.PersistenceClass;

/**
 * Created by Burak Koken on 14.5.2018.
 *
 * @author Burak Koken
 */
public class AnnotationBinder {

    /**
     * Bind the annotated class.
     *
     * @param xClass annotated class
     */
    public static void bindClass(Class xClass, MetadataContext metadataContext) {

        if(isModelClassType(xClass)) {
            PersistenceClass persistenceClass = new PersistenceClass();
            Model modelAnnotation = (Model) xClass.getAnnotation(Model.class);
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
    public static boolean isModelClassType(Class xClass) {

        if(!xClass.isAnnotationPresent(Embeddable.class)) {
            if(!xClass.isAnnotationPresent(Model.class)) {
                throw new AnnotationException("Annotated class should have a @Model or @Embeddable : " +
                    xClass.getName());
            }
            else {
                return true;
            }
        }
        else {

            if(xClass.isAnnotationPresent(Model.class)) {
                throw new AnnotationException("Annotated class should have only a @Model or @Embeddable : " +
                    xClass.getName());
            }

            return false;
        }

    }

}
