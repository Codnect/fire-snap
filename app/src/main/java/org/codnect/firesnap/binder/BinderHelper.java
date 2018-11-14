package org.codnect.firesnap.binder;

import org.codnect.firesnap.annotation.Model;
import org.codnect.firesnap.core.AnnotatedClassType;
import org.codnect.firesnap.core.PropertyHolder;
import org.codnect.firesnap.exception.AnnotationException;
import org.codnect.firesnap.util.StringHelper;
import org.codnect.xreflect.ReflectionManager;
import org.codnect.xreflect.XClass;

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
     * otherwise it returns false.
     */
    public static boolean isEmptyAnnotationValue(String annotationValue) {

        if (annotationValue != null && annotationValue.length() == 0) {
            return true;
        }

        return false;
    }

    /**
     * Get the if the annotated class is a Model class.
     *
     * @param xClass annotated class
     * @return if the annotated class is a Model class, it returns
     * true. Otherwise it returns false.
     */
    public static boolean isModelClassType(AnnotatedClassType annotatedClassType, XClass xClass) {
        if (annotatedClassType == AnnotatedClassType.NONE
                || annotatedClassType == AnnotatedClassType.EMBEDDABLE
                || annotatedClassType == AnnotatedClassType.EMBEDDABLE_SUPERCLASS) {
            return false;
        }

        if (!xClass.isAnnotationPresent(Model.class)) {
            throw new AnnotationException("Annotated class should have only a @Model, @Embeddable or @MappedSuperClass: " +
                    xClass.getName());
        }
        return true;
    }

    /**
     *
     * @param propertyName
     * @param propertyHolder
     * @return
     */
    public static String getRelativePath(String propertyName, PropertyHolder propertyHolder) {
        if(propertyHolder == null) {
            return propertyName;
        }
        String path = propertyHolder.getPath();
        String modelName = propertyHolder.getPersistentClass().getModelName();
        if(path.equals(modelName)) {
            return propertyName;
        } else {
            return StringHelper.qualify(path.substring(modelName.length() + 1), propertyName);
        }
    }

    /**
     *
     * @param xClass
     * @return
     */
    public static boolean isIdPropertType(XClass xClass) {
        ReflectionManager reflectionManager = xClass.getReflectionManager();
        if(reflectionManager.equals(xClass, String.class)
                || reflectionManager.equals(xClass, Long.class)
                || reflectionManager.equals(xClass, long.class)
                || reflectionManager.equals(xClass, Integer.class)
                || reflectionManager.equals(xClass, int.class)
                || reflectionManager.equals(xClass, Short.class)
                || reflectionManager.equals(xClass, short.class)) {
            return true;
        }
        return false;
    }

}
