package com.pragma.traceability_microservice.domain.exceptions;

public class TraceabilityNotFoundException extends RuntimeException {
    public TraceabilityNotFoundException(String message) {
        super(message);
    }
}
