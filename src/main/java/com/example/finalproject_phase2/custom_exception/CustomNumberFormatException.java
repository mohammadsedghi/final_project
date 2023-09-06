package com.example.finalproject_phase2.custom_exception;

public class CustomNumberFormatException extends NumberFormatException{
    public CustomNumberFormatException(String messages) {
        super(messages);
    }
}
