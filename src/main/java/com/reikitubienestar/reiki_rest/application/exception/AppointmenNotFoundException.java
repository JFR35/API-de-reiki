package com.reikitubienestar.reiki_rest.application.exception;

public class AppointmenNotFoundException extends RuntimeException {
    public AppointmenNotFoundException(String message) {
        super(message);
    }
}
