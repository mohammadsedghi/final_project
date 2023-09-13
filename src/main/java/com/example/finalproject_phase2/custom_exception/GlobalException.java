package com.example.finalproject_phase2.custom_exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
//@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(CustomNoResultException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<String> handleNoResultException(CustomNoResultException cre) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cre.getMessage());
    }
    @ExceptionHandler(CustomNumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(CustomNumberFormatException cnfe) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cnfe.getMessage());
    }
    @ExceptionHandler(CustomDuplicateInfoException.class)
    public ResponseEntity<String> handleCustomDuplicateInfoException(CustomDuplicateInfoException cdi) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cdi.getMessage());
    }
}
