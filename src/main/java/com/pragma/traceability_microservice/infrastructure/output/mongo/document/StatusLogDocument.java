package com.pragma.traceability_microservice.infrastructure.output.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "status_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusLogDocument {
    private String lastStatus;
    private String newStatus;
    private LocalDateTime issuedAt;
}
