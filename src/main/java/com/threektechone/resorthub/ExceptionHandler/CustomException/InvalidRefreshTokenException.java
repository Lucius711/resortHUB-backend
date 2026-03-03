package com.threektechone.resorthub.ExceptionHandler.CustomException;

public class InvalidRefreshTokenException extends RuntimeException  {
    public InvalidRefreshTokenException(String message) {
        super(message);
    } 
}
