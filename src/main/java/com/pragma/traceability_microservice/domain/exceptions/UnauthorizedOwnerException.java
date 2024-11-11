package com.pragma.traceability_microservice.domain.exceptions;

public class UnauthorizedOwnerException extends RuntimeException {
    public UnauthorizedOwnerException(String message) {
        super(message);
    }
}
