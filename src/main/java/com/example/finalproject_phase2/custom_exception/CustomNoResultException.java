package com.example.finalproject_phase2.custom_exception;

import jakarta.persistence.NoResultException;

public class CustomNoResultException extends NoResultException {
    public CustomNoResultException(String message) {
        super(message);
    }
}
