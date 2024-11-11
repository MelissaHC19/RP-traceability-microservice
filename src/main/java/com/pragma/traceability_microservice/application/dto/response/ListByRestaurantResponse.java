package com.pragma.traceability_microservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ListByRestaurantResponse {
    private Long orderId;
    private LocalDateTime initialTime;
    private LocalDateTime finalTime;
    private Long employeeId;
    private String employeeEmail;
    private Long restaurantId;
}
