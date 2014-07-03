package org.sweet.bumblebee;

public class BumblebeeException extends RuntimeException {

    public BumblebeeException(String message) {
        super(message);
    }

    public BumblebeeException(String message, Throwable cause) {
        super(message, cause);
    }
}
