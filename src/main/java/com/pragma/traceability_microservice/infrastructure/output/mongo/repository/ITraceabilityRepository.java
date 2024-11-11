package com.pragma.traceability_microservice.infrastructure.output.mongo.repository;

import com.pragma.traceability_microservice.infrastructure.output.mongo.document.TraceabilityDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ITraceabilityRepository extends MongoRepository<TraceabilityDocument, Long> {
    Optional<TraceabilityDocument> findByOrderId(Long orderId);
    List<TraceabilityDocument> findAllByRestaurantId(Long restaurantId);
}
