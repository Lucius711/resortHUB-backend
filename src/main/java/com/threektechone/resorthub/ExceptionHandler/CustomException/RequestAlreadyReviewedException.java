package com.threektechone.resorthub.ExceptionHandler.CustomException;

public class RequestAlreadyReviewedException extends RuntimeException {
    public RequestAlreadyReviewedException(String message) {
        super(message);
    }
}
