package com.pragma.traceability_microservice.application.handler;

import com.pragma.traceability_microservice.application.dto.request.CreateTraceabilityRequest;
import com.pragma.traceability_microservice.application.dto.request.UpdateTraceabilityRequest;
import com.pragma.traceability_microservice.application.dto.response.GetTraceabilityResponse;
import com.pragma.traceability_microservice.application.dto.response.ListByRestaurantResponse;

import java.util.List;

public interface ITraceabilityHandler {
    void createTraceability(CreateTraceabilityRequest createTraceabilityRequest);
    void updateTraceability(UpdateTraceabilityRequest updateTraceabilityRequest, Long orderId);
    GetTraceabilityResponse getTraceabilityByClientAndOrder(Long clientId, Long orderId);
    List<ListByRestaurantResponse> listByRestaurant(Long restaurantId, Long ownerLogged);
    List<ListByRestaurantResponse> listEmployeeRanking(Long restaurantId, Long ownerLogged, Long employeeId);
}
