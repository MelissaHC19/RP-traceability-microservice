package com.pragma.traceability_microservice.application.handler;

public interface IAuthenticationHandler {
    void authentication(String token, String role);
    Long authenticationGetsId(String token, String role);
}
