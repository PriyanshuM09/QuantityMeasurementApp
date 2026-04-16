package com.placement.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to provide clean JSON error messages to the frontend.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        
        // Check for specific error message to provide better status codes
        if (ex.getMessage().contains("User not found")) {
            response.put("message", "User not found. Please sign up first.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (ex.getMessage().contains("User already exists")) {
            response.put("message", "An account with this email already exists.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else if (ex.getMessage().contains("Invalid password")) {
            response.put("message", "Incorrect password. Please try again.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } else if (ex.getMessage().contains("Password login is not available")) {
            response.put("message", "This account uses Google sign-in.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
