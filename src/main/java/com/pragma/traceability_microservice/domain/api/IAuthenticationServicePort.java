package com.pragma.traceability_microservice.domain.api;

public interface IAuthenticationServicePort {
    void authentication(String token, String role);
    Long authenticationGetsId(String token, String role);
}
