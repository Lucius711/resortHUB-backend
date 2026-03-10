package com.threektechone.resorthub.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.threektechone.resorthub.common.exception.custom.DuplicateResourceException;
import com.threektechone.resorthub.common.exception.custom.InvalidEditRequestDataException;
import com.threektechone.resorthub.common.exception.custom.InvalidOtpException;
import com.threektechone.resorthub.common.exception.custom.InvalidRefreshTokenException;
import com.threektechone.resorthub.common.exception.custom.InvalidRegisterStepException;
import com.threektechone.resorthub.common.exception.custom.InvalidResortStatusException;
import com.threektechone.resorthub.common.exception.custom.RequestAlreadyReviewedException;
import com.threektechone.resorthub.common.exception.custom.ResourceNotFoundException;
import com.threektechone.resorthub.common.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class,InvalidRefreshTokenException.class})
    public ResponseEntity<ApiResponse<Object>> handleUnauthorized(Exception ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponse<Object>> handleForbidden(DisabledException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({InvalidOtpException.class,InvalidEditRequestDataException.class})
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(Exception ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({DuplicateResourceException.class,InvalidResortStatusException.class,InvalidRegisterStepException.class
        ,RequestAlreadyReviewedException.class
    })
    public ResponseEntity<ApiResponse<Object>> handleConflict(Exception ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    private ResponseEntity<ApiResponse<Object>> buildResponse(HttpStatus status, String message) {

        ApiResponse<Object> response = new ApiResponse<>(status.value(), message, null, LocalDateTime.now());

        return ResponseEntity.status(status).body(response);
    }
    
}
