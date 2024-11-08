package com.pragma.traceability_microservice.application.mapper;

import com.pragma.traceability_microservice.application.dto.request.CreateTraceabilityRequest;
import com.pragma.traceability_microservice.domain.model.Traceability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICreateTraceabilityRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "finalTime", ignore = true)
    @Mapping(target = "employeeId", ignore = true)
    @Mapping(target = "employeeEmail", ignore = true)
    Traceability createRequestToTraceability(CreateTraceabilityRequest createTraceabilityRequest);
}
