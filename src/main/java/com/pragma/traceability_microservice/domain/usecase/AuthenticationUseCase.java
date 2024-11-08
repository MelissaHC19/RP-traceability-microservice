package com.pragma.traceability_microservice.domain.usecase;

import com.pragma.traceability_microservice.domain.api.IAuthenticationServicePort;
import com.pragma.traceability_microservice.domain.constants.ExceptionConstants;
import com.pragma.traceability_microservice.domain.exceptions.InvalidRoleException;
import com.pragma.traceability_microservice.domain.exceptions.InvalidTokenException;
import com.pragma.traceability_microservice.domain.spi.ITokenProviderPort;

public class AuthenticationUseCase implements IAuthenticationServicePort {
    private final ITokenProviderPort tokenProviderPort;

    public AuthenticationUseCase(ITokenProviderPort tokenProviderPort) {
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public void authentication(String token, String role) {
        validateTokenAndRole(token, role);
    }

    @Override
    public Long authenticationGetsId(String token, String role) {
        validateTokenAndRole(token, role);
        String id = tokenProviderPort.getUserIdFromToken(token);
        return Long.valueOf(id);
    }

    private void validateTokenAndRole(String token, String role) {
        if (!tokenProviderPort.validateToken(token)) {
            throw new InvalidTokenException(ExceptionConstants.INVALID_TOKEN_MESSAGE);
        }

        String roleInToken = tokenProviderPort.getRoleFromToken(token);
        if (!roleInToken.equals(role)) {
            throw new InvalidRoleException(ExceptionConstants.INVALID_ROLE_MESSAGE);
        }
    }
}
