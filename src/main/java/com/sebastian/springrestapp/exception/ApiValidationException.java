package com.sebastian.springrestapp.exception;

public class ApiValidationException extends RuntimeException {
    public ApiValidationException(String message) {
        super(message);
    }
}
