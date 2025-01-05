package com.reikitubienestar.reiki_rest.infraestructure.config;

import com.reikitubienestar.reiki_rest.application.exception.AppointmentNotFoundException;
import com.reikitubienestar.reiki_rest.application.exception.InvalidAppointmentTimeException;
import com.reikitubienestar.reiki_rest.infraestructure.adapters.exception.RequestValidationException;
import com.reikitubienestar.reiki_rest.infraestructure.repositories.exception.DatabaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(InvalidAppointmentTimeException.class)
    public ResponseEntity<String> handleInvalidAppointmentTime(InvalidAppointmentTimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
/*
    @ExceptionHandler(com.reikitubienestar.api_reiki.domain.exceptions.MaxAppointmentsReachedException.class)
    public ResponseEntity<String> handleMaxAppointmentsReached(com.reikitubienestar.api_reiki.domain.exceptions.MaxAppointmentsReachedException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
*/
    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<String> handleAppointmentNotFound(AppointmentNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<String> handleRequestValidationException(RequestValidationException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<String> handleDatabaseException(DatabaseException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());

    }
}