package com.example.finalproject_phase2.custom_exception;

public class CustomDuplicateInfoException extends IllegalArgumentException{
    public CustomDuplicateInfoException(String message) {
        super(message);
    }
}
