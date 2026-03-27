package com.threektechone.resorthub.common.exception.custom;

public class ActiveUnfinishedPaymentException extends RuntimeException {
    public ActiveUnfinishedPaymentException(String message) {
        super(message);
    }
    
}
