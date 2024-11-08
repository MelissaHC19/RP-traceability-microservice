package com.pragma.traceability_microservice.domain.exceptions;

public class UnauthorizedClientException extends RuntimeException {
    public UnauthorizedClientException(String message) {
        super(message);
    }
}
