package org.codnect.firesnap.binder;

import org.codnect.firesnap.annotation.InheritanceStrategy;
import org.codnect.firesnap.annotation.Model;
import org.codnect.firesnap.core.AnnotatedClassType;
import org.codnect.firesnap.core.InheritanceState;
import org.codnect.firesnap.exception.AnnotationException;
import org.codnect.firesnap.reflection.XClass;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public class BinderHelper {

    /**
     * Check if annotation value is empty.
     *
     * @param annotationValue annotation value
     * @return if annotation value is empty, it returns true
     *         otherwise it returns false.
     */
    public static boolean isEmptyAnnotationValue(String annotationValue) {

        if(annotationValue != null && annotationValue.length() == 0) {
            return true;
        }

        return false;
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

}
