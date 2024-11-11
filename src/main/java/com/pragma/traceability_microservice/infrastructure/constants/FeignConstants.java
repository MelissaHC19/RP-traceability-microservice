package com.pragma.traceability_microservice.infrastructure.constants;

public class FeignConstants {
    private FeignConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FEIGN_CLIENT_NAME_RESTAURANT = "foodcourt-microservice";
    public static final String FEIGN_CLIENT_URL_RESTAURANT = "localhost:8090/restaurant";
}
