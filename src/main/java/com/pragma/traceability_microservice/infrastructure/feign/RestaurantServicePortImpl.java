package com.pragma.traceability_microservice.infrastructure.feign;

import com.pragma.traceability_microservice.application.dto.request.GetRestaurantsOwnerRequest;
import com.pragma.traceability_microservice.domain.api.IRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestaurantServicePortImpl implements IRestaurantServicePort {
    private final IRestaurantFeign restaurantFeign;

    @Override
    public Long getRestaurantsOwner(Long restaurantId) {
        GetRestaurantsOwnerRequest restaurantsOwnerRequest = restaurantFeign.getRestaurantsOwner(restaurantId);
        return restaurantsOwnerRequest.getOwner();
    }
}
