package com.threektechone.resorthub.common.exception.custom;

public class InvalidBookingStatusException extends RuntimeException {
    public InvalidBookingStatusException(String message) {
        super(message);
    }
}
