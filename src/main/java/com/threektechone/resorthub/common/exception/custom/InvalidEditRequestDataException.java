package com.threektechone.resorthub.common.exception.custom;

public class InvalidEditRequestDataException extends RuntimeException {
    public InvalidEditRequestDataException(String message,Throwable cause) {
        super(message,cause);
    }
}
