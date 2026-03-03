package com.threektechone.resorthub.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.threektechone.resorthub.ExceptionHandler.CustomException.DuplicateResourceException;
import com.threektechone.resorthub.ExceptionHandler.CustomException.InvalidOtpException;
import com.threektechone.resorthub.ExceptionHandler.CustomException.InvalidRefreshTokenException;
import com.threektechone.resorthub.ExceptionHandler.CustomException.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class,InvalidRefreshTokenException.class})
    public ResponseEntity<ExceptionResponse> handleUnauthorized(Exception ex) {
    return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ExceptionResponse(401, ex.getMessage()));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleForbidden(DisabledException ex) {
    return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new ExceptionResponse(403, ex.getMessage()));
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(ResourceNotFoundException ex) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ExceptionResponse(404, ex.getMessage()));
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequest(InvalidOtpException ex) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ExceptionResponse(400, ex.getMessage()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ExceptionResponse> handleConflict(DuplicateResourceException ex) {
    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ExceptionResponse(409, ex.getMessage()));
    }
    
}
