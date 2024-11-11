package com.pragma.traceability_microservice.domain.usecase;

import com.pragma.traceability_microservice.domain.api.IRestaurantServicePort;
import com.pragma.traceability_microservice.domain.api.ITraceabilityServicePort;
import com.pragma.traceability_microservice.domain.constants.ExceptionConstants;
import com.pragma.traceability_microservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.traceability_microservice.domain.exceptions.TraceabilityNotFoundException;
import com.pragma.traceability_microservice.domain.exceptions.UnauthorizedClientException;
import com.pragma.traceability_microservice.domain.exceptions.UnauthorizedOwnerException;
import com.pragma.traceability_microservice.domain.model.StatusLog;
import com.pragma.traceability_microservice.domain.model.Traceability;
import com.pragma.traceability_microservice.domain.spi.ITraceabilityPersistencePort;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class TraceabilityUseCase implements ITraceabilityServicePort {
    private final ITraceabilityPersistencePort traceabilityPersistencePort;
    private final IRestaurantServicePort restaurantServicePort;

    public TraceabilityUseCase(ITraceabilityPersistencePort traceabilityPersistencePort, IRestaurantServicePort restaurantServicePort) {
        this.traceabilityPersistencePort = traceabilityPersistencePort;
        this.restaurantServicePort = restaurantServicePort;
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

    @Override
    public List<Traceability> listByRestaurant(Long restaurantId, Long ownerLogged) {
        Long ownerId = restaurantServicePort.getRestaurantsOwner(restaurantId);
        validateIfOwnerOwnsRestaurant(ownerId, ownerLogged);
        return traceabilityPersistencePort.findTraceabilityByRestaurantId(restaurantId);
    }

    @Override
    public List<Traceability> listEmployeeRanking(Long restaurantId, Long ownerLogged, Long employeeId) {
        List<Traceability> restaurantList = listByRestaurant(restaurantId, ownerLogged);
        if (restaurantList.isEmpty()) {
            return List.of();
        }
        return restaurantList.stream()
                .filter(traceability -> traceability.getEmployeeId() != null && traceability.getEmployeeId().equals(employeeId))
                .sorted(Comparator.comparing(traceability ->
                        Duration.between(traceability.getInitialTime(), traceability.getFinalTime()).toMillis()))
                .toList();
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

    private void validateIfOwnerOwnsRestaurant(Long ownerId, Long ownerLogged) {
        if (ownerId == 0) {
            throw new RestaurantNotFoundException(ExceptionConstants.RESTAURANT_NOT_FOUND_MESSAGE);
        } else if (!Objects.equals(ownerId, ownerLogged)) {
            throw new UnauthorizedOwnerException(ExceptionConstants.UNAUTHORIZED_OWNER_MESSAGE);
        }
    }
}
