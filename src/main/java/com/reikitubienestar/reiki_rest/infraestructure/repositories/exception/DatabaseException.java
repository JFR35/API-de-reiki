package com.reikitubienestar.reiki_rest.infraestructure.repositories.exception;



public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}