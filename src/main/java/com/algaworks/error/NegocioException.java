package com.algaworks.error;

public class NegocioException extends RuntimeException {

    public NegocioException(String message) {super(message);};
    public NegocioException(String message, Throwable cause) {super(message, cause);};
}
