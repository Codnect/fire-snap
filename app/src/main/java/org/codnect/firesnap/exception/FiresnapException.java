package org.codnect.firesnap.exception;

/**
 * Created by Burak Koken on 14.5.2018.
 *
 * @author Burak Koken
 */
public class FireSnapException extends RuntimeException {

    public FireSnapException(String message) {
        super(message);
    }

    public FireSnapException(Throwable cause) {
        super(cause);
    }

    public FireSnapException(String message, Throwable cause) {
        super(message, cause);
    }

}
