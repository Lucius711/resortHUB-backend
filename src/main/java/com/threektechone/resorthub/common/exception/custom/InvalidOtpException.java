package com.threektechone.resorthub.common.exception.custom;

public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String message) {
        super(message);
    }
}