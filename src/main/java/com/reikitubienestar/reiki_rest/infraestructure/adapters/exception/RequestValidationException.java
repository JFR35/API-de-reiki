package com.reikitubienestar.reiki_rest.infraestructure.adapters.exception;

public class RequestValidationException extends RuntimeException {
    public RequestValidationException(String message) {
        super(message);
    }
}
