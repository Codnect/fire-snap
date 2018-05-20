package org.codnect.firesnap.binder;

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

}
