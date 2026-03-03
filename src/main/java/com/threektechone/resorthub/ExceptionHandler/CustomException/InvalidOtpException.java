package com.threektechone.resorthub.ExceptionHandler.CustomException;

public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String message) {
        super(message);
    }
}