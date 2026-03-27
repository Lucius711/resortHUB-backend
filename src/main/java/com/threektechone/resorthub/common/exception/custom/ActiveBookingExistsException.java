package com.threektechone.resorthub.common.exception.custom;

public class ActiveBookingExistsException extends RuntimeException {
    public ActiveBookingExistsException(String message) {
        super(message);
    }
}
