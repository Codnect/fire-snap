package org.codnect.firesnap.exception;

/**
 * Created by Burak Koken on 10.9.2018.
 *
 * @author Burak Koken
 */
public class NotResolvedException extends MappingException {

    public NotResolvedException(String message) {
        super(message);
    }

    public NotResolvedException(Throwable cause) {
        super(cause);
    }

    public NotResolvedException(String message, Throwable cause) {
        super(message, cause);
    }

}
