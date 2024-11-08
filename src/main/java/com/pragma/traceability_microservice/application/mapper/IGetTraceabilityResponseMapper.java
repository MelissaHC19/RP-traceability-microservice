package com.pragma.traceability_microservice.application.mapper;

import com.pragma.traceability_microservice.application.dto.response.GetTraceabilityResponse;
import com.pragma.traceability_microservice.domain.model.Traceability;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IGetTraceabilityResponseMapper {
    GetTraceabilityResponse traceabilityToResponse(Traceability traceability);
}
