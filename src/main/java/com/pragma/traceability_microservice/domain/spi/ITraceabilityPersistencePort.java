package com.pragma.traceability_microservice.domain.spi;

import com.pragma.traceability_microservice.domain.model.Traceability;

import java.util.List;

public interface ITraceabilityPersistencePort {
    void createTraceability(Traceability traceability);
    void updateTraceability(Traceability traceability);
    Traceability findTraceabilityByOrderId(Long orderId);
    List<Traceability> findTraceabilityByRestaurantId(Long restaurantId);
}
