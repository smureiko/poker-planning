package com.interview.challenge.pokerplanning.exception;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<ErrorMessage> handleNoSuchElementException(NoSuchElementException ex) {
        ErrorMessage message = new ErrorMessage();
        message.setMessage(ex.getMessage());
        message.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> handleGlobalException(Exception ex) {
        ErrorMessage message = new ErrorMessage();
        message.setMessage(ex.getMessage());
        message.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
