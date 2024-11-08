package com.pragma.traceability_microservice.infrastructure.exceptionhandler;

import com.pragma.traceability_microservice.application.dto.response.ExceptionResponse;
import com.pragma.traceability_microservice.domain.exceptions.InvalidRoleException;
import com.pragma.traceability_microservice.domain.exceptions.InvalidTokenException;
import com.pragma.traceability_microservice.domain.exceptions.TraceabilityNotFoundException;
import com.pragma.traceability_microservice.domain.exceptions.UnauthorizedClientException;
import com.pragma.traceability_microservice.infrastructure.constants.ControllerConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidRoleException(InvalidRoleException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), HttpStatus.FORBIDDEN.toString(), LocalDateTime.now());
        return ResponseEntity.status(403).body(response);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidTokenException(InvalidTokenException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED.toString(), LocalDateTime.now());
        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler(TraceabilityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleOrderNotFoundException(TraceabilityNotFoundException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(UnauthorizedClientException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedClientException(UnauthorizedClientException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), HttpStatus.FORBIDDEN.toString(), LocalDateTime.now());
        return ResponseEntity.status(403).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptionsDTO(MethodArgumentNotValidException exception) {
        ArrayList<String> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(
                error -> errors.add(error.getDefaultMessage())
        );
        Map<String, Object> response = new HashMap<>();
        response.put(ControllerConstants.TIMESTAMP, LocalDateTime.now().toString());
        response.put(ControllerConstants.ERRORS, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
