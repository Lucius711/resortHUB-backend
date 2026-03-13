package com.threektechone.resorthub.common.exception.custom;

public class BookingCancelForbiddenException extends RuntimeException {
    public BookingCancelForbiddenException(String message) {
        super(message);
    }
}
