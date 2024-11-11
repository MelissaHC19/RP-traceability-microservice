package com.pragma.traceability_microservice.infrastructure.feign;

import com.pragma.traceability_microservice.application.dto.request.GetRestaurantsOwnerRequest;
import com.pragma.traceability_microservice.infrastructure.constants.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = FeignConstants.FEIGN_CLIENT_NAME_RESTAURANT, url = FeignConstants.FEIGN_CLIENT_URL_RESTAURANT)
public interface IRestaurantFeign {
    @GetMapping("/{restaurantId}")
    GetRestaurantsOwnerRequest getRestaurantsOwner(@PathVariable Long restaurantId);
}
