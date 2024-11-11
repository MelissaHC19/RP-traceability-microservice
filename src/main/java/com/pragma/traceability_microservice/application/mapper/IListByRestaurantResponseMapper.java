package com.pragma.traceability_microservice.application.mapper;

import com.pragma.traceability_microservice.application.dto.response.ListByRestaurantResponse;
import com.pragma.traceability_microservice.domain.model.Traceability;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IListByRestaurantResponseMapper {
    List<ListByRestaurantResponse> traceabilityToResponse(List<Traceability> traceabilityList);
}
