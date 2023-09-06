package com.example.finalproject_phase2.custom_exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
//    public ResponseEntity<Map<String,String>> handleInValidException(ConstraintViolationException e) {
//        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
//        Map<String,String> messages = new HashMap<>();
//        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
//            messages.put(constraintViolation.getPropertyPath().toString(),constraintViolation.getMessageTemplate());
//        }
//        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(messages);
//    }
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//    }


//    @ExceptionHandler(BindException.class)
//    public ResponseEntity<Map<String, String>> handleBindExceptions(BindException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//    }
}
