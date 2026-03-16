package com.threektechone.resorthub.common.exception.custom;

public class CapacityExceededException extends RuntimeException {
    public CapacityExceededException(String message) {
        super(message);
    }
}
