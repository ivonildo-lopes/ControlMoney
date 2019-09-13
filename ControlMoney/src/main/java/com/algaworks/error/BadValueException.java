package com.algaworks.error;

public class BadValueException extends RuntimeException {

    public BadValueException(String message) {super(message);};
    public BadValueException(String message, Throwable cause) {super(message, cause);};
}
