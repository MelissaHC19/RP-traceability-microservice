package com.pragma.traceability_microservice.application.dto.request;

import com.pragma.traceability_microservice.domain.model.StatusLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class CreateTraceabilityRequest {
    private Long orderId;
    private Long clientId;
    private String clientEmail;
    private LocalDateTime initialTime;
    private List<StatusLog> statusLogs;
    private Long restaurantId;
}
