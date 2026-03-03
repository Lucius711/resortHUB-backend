package com.threektechone.resorthub.ExceptionHandler.CustomException;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
