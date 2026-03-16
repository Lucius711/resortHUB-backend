package com.threektechone.resorthub.common.exception.custom;

public class CheckInNotAllowedException extends RuntimeException{
     public CheckInNotAllowedException(String message) {
        super(message);
    }
}
