package com.threektechone.resorthub.common.exception.custom;

public class InvalidRefreshTokenException extends RuntimeException  {
    public InvalidRefreshTokenException(String message) {
        super(message);
    } 
}
