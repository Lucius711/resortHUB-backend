package com.threektechone.resorthub.common.exception.custom;

public class CheckOutNotAllowedException extends RuntimeException {
    public CheckOutNotAllowedException(String message) {
        super(message);
    }
}
