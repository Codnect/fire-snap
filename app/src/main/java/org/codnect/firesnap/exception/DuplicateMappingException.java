package org.codnect.firesnap.exception;

/**
 * Created by Burak Koken on 29.9.2018.
 *
 * @author Burak Koken
 */
public class DuplicateMappingException extends PersistenceException {

    public DuplicateMappingException(String message) {
        super(message);
    }

    public DuplicateMappingException(Throwable cause) {
        super(cause);
    }

    public DuplicateMappingException(String message, Throwable cause) {
        super(message, cause);
    }

}
