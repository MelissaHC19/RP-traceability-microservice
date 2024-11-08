package com.pragma.traceability_microservice.infrastructure.output.mongo.adapter;

import com.pragma.traceability_microservice.domain.model.Traceability;
import com.pragma.traceability_microservice.domain.spi.ITraceabilityPersistencePort;
import com.pragma.traceability_microservice.infrastructure.output.mongo.mapper.ITraceabilityDocumentMapper;
import com.pragma.traceability_microservice.infrastructure.output.mongo.repository.ITraceabilityRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TraceabilityMongoAdapter implements ITraceabilityPersistencePort {
    private final ITraceabilityRepository traceabilityRepository;
    private final ITraceabilityDocumentMapper traceabilityDocumentMapper;

    @Override
    public void createTraceability(Traceability traceability) {
        traceabilityRepository.save(traceabilityDocumentMapper.traceabilityToDocument(traceability));
    }

    @Override
    public void updateTraceability(Traceability traceability) {
        traceabilityRepository.save(traceabilityDocumentMapper.traceabilityToDocument(traceability));
    }

    @Override
    public Traceability findTraceabilityByOrderId(Long orderId) {
        return traceabilityDocumentMapper.traceabilityDocumentToTraceability(traceabilityRepository.findByOrderId(orderId).orElse(null));
    }
}
