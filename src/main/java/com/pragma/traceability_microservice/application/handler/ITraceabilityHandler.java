package com.pragma.traceability_microservice.application.handler;

import com.pragma.traceability_microservice.application.dto.request.CreateTraceabilityRequest;
import com.pragma.traceability_microservice.application.dto.request.UpdateTraceabilityRequest;
import com.pragma.traceability_microservice.application.dto.response.GetTraceabilityResponse;

public interface ITraceabilityHandler {
    void createTraceability(CreateTraceabilityRequest createTraceabilityRequest);
    void updateTraceability(UpdateTraceabilityRequest updateTraceabilityRequest, Long orderId);
    GetTraceabilityResponse getTraceabilityByClientAndOrder(Long clientId, Long orderId);
}
