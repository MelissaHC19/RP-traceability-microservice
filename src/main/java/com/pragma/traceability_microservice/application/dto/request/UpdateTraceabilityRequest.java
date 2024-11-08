package com.pragma.traceability_microservice.application.dto.request;

import com.pragma.traceability_microservice.domain.model.StatusLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UpdateTraceabilityRequest {
    private LocalDateTime finalTime;
    private Long employeeId;
    private String employeeEmail;
    private List<StatusLog> statusLogs;
}
