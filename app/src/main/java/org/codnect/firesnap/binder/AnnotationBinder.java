package org.codnect.firesnap.binder;

import org.codnect.firesnap.annotation.Embeddable;
import org.codnect.firesnap.annotation.Model;
import org.codnect.firesnap.exception.AnnotationException;

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
    public static void bindClass(Class xClass) {

        if(isModelClassType(xClass)) {
            Model modelAnnotation = (Model) xClass.getAnnotation(Model.class);
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
