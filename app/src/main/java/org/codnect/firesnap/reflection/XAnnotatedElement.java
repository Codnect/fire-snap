package org.codnect.firesnap.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * Created by Burak Koken on 24.5.2018.
 *
 * @author Burak Koken
 */
public class XAnnotatedElement implements AnnotatedElement {

    private AnnotatedElement annotatedElement;

    public XAnnotatedElement(AnnotatedElement annotatedElement) {
        this.annotatedElement = annotatedElement;
    }

    /**
     * Get the annotated element for this object.
     *
     * @return annotated element
     */
    public AnnotatedElement toAnnotatedElement() {
        return annotatedElement;
    }

    /**
     * Returns this element's annotation for the specified type if
     * such an annotation is present, else null.
     *
     * @param annotationClass the Class object corresponding to the
     * annotation type
     * @return this element's annotation for the specified annotation type if
     * present on this element, else null
     */
    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return annotatedElement.getAnnotation(annotationClass);
    }

    /**
     * Returns all annotations present on this element.
     *
     * @return all annotations present on this element
     */
    @Override
    public Annotation[] getAnnotations() {
        return annotatedElement.getAnnotations();
    }

    /**
     * Returns all annotations that are directly present on this
     * element.
     *
     * @return All annotations directly present on this element
     */
    @Override
    public Annotation[] getDeclaredAnnotations() {
        return annotatedElement.getDeclaredAnnotations();
    }

    /**
     * Returns true if an annotation for the specified type
     * is present on this element, else false.
     *
     * @param annotationClass the Class object corresponding to the
     *        annotation type
     * @return true if an annotation for the specified annotation
     * type is present on this element, else false
     */
    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return annotatedElement.isAnnotationPresent(annotationClass);
    }

    /**
     * Compare this object to another object, if they are
     * same, it returns true. Otherwise, it returns false.
     *
     * @param obj another object
     * @return if they are same, it returns true. Otherwise,
     * it returns false.
     */
    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof XAnnotatedElement)) {
            return false;
        }

        XAnnotatedElement xAnnotatedElement = (XAnnotatedElement) obj;
        return annotatedElement.equals(xAnnotatedElement.toAnnotatedElement());
    }

    /**
     * Get the hash code value for annotated element.
     *
     * @return hash code value
     */
    @Override
    public int hashCode() {
        return annotatedElement.hashCode();
    }

    /**
     * Returns a string representation of the XAnnotatedElement.
     *
     * @return a string representation of the XAnnotatedElement
     */
    @Override
    public String toString() {
        return annotatedElement.toString();
    }

}
