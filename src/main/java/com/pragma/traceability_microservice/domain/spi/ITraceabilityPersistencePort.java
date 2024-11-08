package com.pragma.traceability_microservice.domain.spi;

import com.pragma.traceability_microservice.domain.model.Traceability;

public interface ITraceabilityPersistencePort {
    void createTraceability(Traceability traceability);
    void updateTraceability(Traceability traceability);
    Traceability findTraceabilityByOrderId(Long orderId);
}
