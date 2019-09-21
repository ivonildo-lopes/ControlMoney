package com.algaworks.error;

public class NoContentException extends RuntimeException {

    public NoContentException(String message) {super(message);};
    public NoContentException(String message, Throwable cause) {super(message, cause);};
}
