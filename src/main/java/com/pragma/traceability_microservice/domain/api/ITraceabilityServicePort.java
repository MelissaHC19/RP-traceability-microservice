package com.pragma.traceability_microservice.domain.api;

import com.pragma.traceability_microservice.domain.model.Traceability;

public interface ITraceabilityServicePort {
    void createTraceability(Traceability traceability);
    void updateTraceability(Traceability traceability, Long orderId);
    Traceability getTraceabilityByClientAndOrder(Long clientId, Long orderId);
}
