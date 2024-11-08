package com.pragma.traceability_microservice.infrastructure.output.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "traceability")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TraceabilityDocument {
    @Id
    private String id;
    private Long orderId;
    private Long clientId;
    private String clientEmail;
    private LocalDateTime initialTime;
    private LocalDateTime finalTime;
    private List<StatusLogDocument> statusLogs;
    private Long employeeId;
    private String employeeEmail;
}
