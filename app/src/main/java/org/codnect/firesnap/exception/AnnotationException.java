package org.codnect.firesnap.exception;

/**
 * Created by Burak Koken on 14.5.2018.
 *
 * @author Burak Koken
 */
public class AnnotationException extends MappingException {

    public AnnotationException(String message) {
        super(message);
    }

    public AnnotationException(Throwable cause) {
        super(cause);
    }

    public AnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

}
