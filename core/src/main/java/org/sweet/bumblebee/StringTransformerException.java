package org.sweet.bumblebee;

public class StringTransformerException extends RuntimeException {

    public StringTransformerException(String message) {
        super(message);
    }

    public StringTransformerException(String message, Throwable cause) {
        super(message, cause);
    }
}
