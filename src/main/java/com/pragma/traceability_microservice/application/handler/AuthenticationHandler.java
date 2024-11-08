package com.pragma.traceability_microservice.application.handler;

import com.pragma.traceability_microservice.application.constants.HandlerConstants;
import com.pragma.traceability_microservice.domain.api.IAuthenticationServicePort;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationHandler implements IAuthenticationHandler {
    private final IAuthenticationServicePort authenticationServicePort;

    @Override
    public void authentication(String token, String role) {
        authenticationServicePort.authentication(StringUtils.removeStart(token, HandlerConstants.BEARER), role);
    }

    @Override
    public Long authenticationGetsId(String token, String role) {
        return authenticationServicePort.authenticationGetsId(StringUtils.removeStart(token, HandlerConstants.BEARER), role);
    }
}
