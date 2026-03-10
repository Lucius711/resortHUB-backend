package com.threektechone.resorthub.common.exception.custom;

public class RequestAlreadyReviewedException extends RuntimeException {
    public RequestAlreadyReviewedException(String message) {
        super(message);
    }
}
