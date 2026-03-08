package com.threektechone.resorthub.ExceptionHandler.CustomException;

public class InvalidResortStatusException extends RuntimeException {
    public InvalidResortStatusException(String message) {
        super(message);
    }
    
}
