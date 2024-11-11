package com.pragma.traceability_microservice.domain.api;

import com.pragma.traceability_microservice.domain.model.Traceability;

import java.util.List;

public interface ITraceabilityServicePort {
    void createTraceability(Traceability traceability);
    void updateTraceability(Traceability traceability, Long orderId);
    Traceability getTraceabilityByClientAndOrder(Long clientId, Long orderId);
    List<Traceability> listByRestaurant(Long restaurantId, Long ownerLogged);
    List<Traceability> listEmployeeRanking(Long restaurantId, Long ownerLogged, Long employeeId);
}
