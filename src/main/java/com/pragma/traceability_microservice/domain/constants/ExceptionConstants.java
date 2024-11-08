package com.pragma.traceability_microservice.domain.constants;

public class ExceptionConstants {
    private ExceptionConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String INVALID_TOKEN_MESSAGE = "Invalid token.";
    public static final String INVALID_ROLE_MESSAGE = "Access denied. You don't have permission to access this resource.";
    public static final String TRACEABILITY_NOT_FOUND_MESSAGE = "Traceability for the order not found or doesn't exists.";
    public static final String UNAUTHORIZED_CLIENT_MESSAGE = "Can't check order traceability from an order you didn't placed.";
}
