package com.firstclub.core.controller;

import com.firstclub.core.dto.ErrorResponseDTO;
import com.firstclub.core.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler (Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO( 500, "INTERNAL_SERVER_ERROR", "An unexpected error occurred.");
        return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Invalid input: {}", ex.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(400, "INVALID_INPUT", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException( ResourceNotFoundException ex) {
        log.info("Resource not found: {}", ex.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(404, "RESOURCE_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
