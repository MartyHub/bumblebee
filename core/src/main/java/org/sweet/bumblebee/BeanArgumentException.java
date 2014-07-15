package org.sweet.bumblebee;

public class BeanArgumentException extends RuntimeException {

    public BeanArgumentException(String message) {
        super(message);
    }

    public BeanArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
