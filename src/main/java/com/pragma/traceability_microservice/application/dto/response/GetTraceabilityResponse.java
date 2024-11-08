package com.pragma.traceability_microservice.application.dto.response;

import com.pragma.traceability_microservice.domain.model.StatusLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetTraceabilityResponse {
    private Long orderId;
    private Long clientId;
    private String clientEmail;
    private LocalDateTime initialTime;
    private LocalDateTime finalTime;
    private List<StatusLog> statusLogs;
    private Long employeeId;
    private String employeeEmail;
}
