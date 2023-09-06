package com.example.finalproject_phase2.custom_exception;

import jakarta.transaction.TransactionalException;

public class CustomTransactionException extends Exception {
    public CustomTransactionException(String message) {
        super(message);
    }
}
