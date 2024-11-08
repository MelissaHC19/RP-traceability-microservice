package com.pragma.traceability_microservice.domain.usecase;

import com.pragma.traceability_microservice.domain.api.ITraceabilityServicePort;
import com.pragma.traceability_microservice.domain.constants.ExceptionConstants;
import com.pragma.traceability_microservice.domain.exceptions.TraceabilityNotFoundException;
import com.pragma.traceability_microservice.domain.exceptions.UnauthorizedClientException;
import com.pragma.traceability_microservice.domain.model.StatusLog;
import com.pragma.traceability_microservice.domain.model.Traceability;
import com.pragma.traceability_microservice.domain.spi.ITraceabilityPersistencePort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TraceabilityUseCase implements ITraceabilityServicePort {
    private final ITraceabilityPersistencePort traceabilityPersistencePort;

    public TraceabilityUseCase(ITraceabilityPersistencePort traceabilityPersistencePort) {
        this.traceabilityPersistencePort = traceabilityPersistencePort;
    }

    @Override
    public void createTraceability(Traceability traceability) {
        traceabilityPersistencePort.createTraceability(traceability);
    }

    @Override
    public void updateTraceability(Traceability traceability, Long orderId) {
        Traceability newTraceability = traceabilityPersistencePort.findTraceabilityByOrderId(orderId);
        newTraceability.setEmployeeId(traceability.getEmployeeId());
        newTraceability.setEmployeeEmail(traceability.getEmployeeEmail());

        List<StatusLog> existingStatusLog = newTraceability.getStatusLogs();
        if (existingStatusLog == null) {
            existingStatusLog = new ArrayList<>();
        } else {
            existingStatusLog = new ArrayList<>(existingStatusLog);
        }

        if (traceability.getStatusLogs() != null && !traceability.getStatusLogs().isEmpty()) {
            existingStatusLog.add(traceability.getStatusLogs().get(0));
        }

        newTraceability.setStatusLogs(existingStatusLog);

        if (traceability.getFinalTime() != null) {
            newTraceability.setFinalTime(traceability.getFinalTime());
        }
        traceabilityPersistencePort.updateTraceability(newTraceability);
    }

    @Override
    public Traceability getTraceabilityByClientAndOrder(Long clientId, Long orderId) {
        Traceability traceability = traceabilityPersistencePort.findTraceabilityByOrderId(orderId);
        validateTraceabilityExistence(traceability);
        validateIfClientOwnsOrder(clientId, traceability.getClientId());
        return traceability;
    }

    private void validateTraceabilityExistence(Traceability traceability) {
        if (traceability == null) {
            throw new TraceabilityNotFoundException(ExceptionConstants.TRACEABILITY_NOT_FOUND_MESSAGE);
        }
    }

    private void validateIfClientOwnsOrder(Long clientId, Long orderClientId) {
        if (!Objects.equals(clientId, orderClientId)) {
            throw new UnauthorizedClientException(ExceptionConstants.UNAUTHORIZED_CLIENT_MESSAGE);
        }
    }
}
