package com.pragma.traceability_microservice.application.mapper;

import com.pragma.traceability_microservice.application.dto.request.UpdateTraceabilityRequest;
import com.pragma.traceability_microservice.domain.model.Traceability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUpdateTraceabilityRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "clientEmail", ignore = true)
    @Mapping(target = "initialTime", ignore = true)
    Traceability updateRequestToTraceability(UpdateTraceabilityRequest updateTraceabilityRequest);
}
