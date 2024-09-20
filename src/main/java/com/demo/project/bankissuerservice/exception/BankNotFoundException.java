package com.demo.project.bankissuerservice.exception;

public class BankNotFoundException extends RuntimeException {

    public BankNotFoundException(String message) {
        super(message);
    }
}
