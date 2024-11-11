package com.pragma.traceability_microservice.infrastructure.input.rest;

import com.pragma.traceability_microservice.application.dto.request.CreateTraceabilityRequest;
import com.pragma.traceability_microservice.application.dto.request.UpdateTraceabilityRequest;
import com.pragma.traceability_microservice.application.dto.response.GetTraceabilityResponse;
import com.pragma.traceability_microservice.application.dto.response.ListByRestaurantResponse;
import com.pragma.traceability_microservice.application.handler.IAuthenticationHandler;
import com.pragma.traceability_microservice.application.handler.ITraceabilityHandler;
import com.pragma.traceability_microservice.infrastructure.constants.ControllerConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/traceability")
@RequiredArgsConstructor
public class TraceabilityRestControllerAdapter {
    private final ITraceabilityHandler traceabilityHandler;
    private final IAuthenticationHandler authenticationHandler;

    @PostMapping("/create")
    public ResponseEntity<Void> createTraceability(@Valid @RequestBody CreateTraceabilityRequest request) {
        traceabilityHandler.createTraceability(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Void> updateTraceability(@PathVariable Long orderId, @Valid @RequestBody UpdateTraceabilityRequest request) {
        traceabilityHandler.updateTraceability(request, orderId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GetTraceabilityResponse> getTraceabilityByOrderAndClient(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long orderId) {
        Long clientId = authenticationHandler.authenticationGetsId(token, ControllerConstants.ROLE_CLIENT);
        GetTraceabilityResponse traceability = traceabilityHandler.getTraceabilityByClientAndOrder(clientId, orderId);
        return ResponseEntity.status(HttpStatus.OK).body(traceability);
    }

    @GetMapping("/list/orders/{restaurantId}")
    public ResponseEntity<List<ListByRestaurantResponse>> listByRestaurant(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long restaurantId) {
        Long ownerId = authenticationHandler.authenticationGetsId(token, ControllerConstants.ROLE_OWNER);
        List<ListByRestaurantResponse> listOrders = traceabilityHandler.listByRestaurant(restaurantId, ownerId);
        return ResponseEntity.status(HttpStatus.OK).body(listOrders);
    }

    @GetMapping("/list/ranking/{restaurantId}/{employeeId}")
    public ResponseEntity<List<ListByRestaurantResponse>> listEmployeeRanking(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long restaurantId, @PathVariable Long employeeId) {
        Long ownerId = authenticationHandler.authenticationGetsId(token, ControllerConstants.ROLE_OWNER);
        List<ListByRestaurantResponse> listOrdersRanking = traceabilityHandler.listEmployeeRanking(restaurantId, ownerId, employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(listOrdersRanking);
    }
}