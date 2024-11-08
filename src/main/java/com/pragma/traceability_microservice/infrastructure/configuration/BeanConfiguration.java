package com.pragma.traceability_microservice.infrastructure.configuration;

import com.pragma.traceability_microservice.domain.api.IAuthenticationServicePort;
import com.pragma.traceability_microservice.domain.api.ITraceabilityServicePort;
import com.pragma.traceability_microservice.domain.spi.ITokenProviderPort;
import com.pragma.traceability_microservice.domain.spi.ITraceabilityPersistencePort;
import com.pragma.traceability_microservice.domain.usecase.AuthenticationUseCase;
import com.pragma.traceability_microservice.domain.usecase.TraceabilityUseCase;
import com.pragma.traceability_microservice.infrastructure.output.mongo.adapter.TraceabilityMongoAdapter;
import com.pragma.traceability_microservice.infrastructure.output.mongo.mapper.ITraceabilityDocumentMapper;
import com.pragma.traceability_microservice.infrastructure.output.mongo.repository.ITraceabilityRepository;
import com.pragma.traceability_microservice.infrastructure.security.adapter.TokenAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ITraceabilityRepository traceabilityRepository;
    private final ITraceabilityDocumentMapper traceabilityDocumentMapper;

    @Bean
    public ITokenProviderPort tokenProviderPort() {
        return new TokenAdapter();
    }

    @Bean
    public IAuthenticationServicePort authenticationServicePort() {
        return new AuthenticationUseCase(tokenProviderPort());
    }

    @Bean
    public ITraceabilityPersistencePort traceabilityPersistencePort() {
        return new TraceabilityMongoAdapter(traceabilityRepository, traceabilityDocumentMapper);
    }

    @Bean
    public ITraceabilityServicePort traceabilityServicePort() {
        return new TraceabilityUseCase(traceabilityPersistencePort());
    }
}
