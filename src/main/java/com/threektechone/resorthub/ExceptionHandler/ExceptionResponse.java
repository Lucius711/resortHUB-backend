package com.threektechone.resorthub.ExceptionHandler;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private int status;
    private String error;
    private LocalDateTime timestamp;

    public ExceptionResponse() {};

    public ExceptionResponse(int status, String error) {
        this.status = status;
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
