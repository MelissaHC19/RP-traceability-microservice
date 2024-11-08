package com.pragma.traceability_microservice.domain.usecase;

import com.pragma.traceability_microservice.domain.constants.ExceptionConstants;
import com.pragma.traceability_microservice.domain.exceptions.InvalidTokenException;
import com.pragma.traceability_microservice.domain.exceptions.TraceabilityNotFoundException;
import com.pragma.traceability_microservice.domain.exceptions.UnauthorizedClientException;
import com.pragma.traceability_microservice.domain.model.StatusLog;
import com.pragma.traceability_microservice.domain.model.Traceability;
import com.pragma.traceability_microservice.domain.spi.ITraceabilityPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TraceabilityUseCaseTest {
    @Mock
    private ITraceabilityPersistencePort traceabilityPersistencePort;

    @InjectMocks
    private TraceabilityUseCase traceabilityUseCase;

    @Test
    @DisplayName("Created order traceability successfully")
    void createTraceability() {
        Traceability traceability = new Traceability("jhsbdjhd", 1L, 1L, "email@email.com", LocalDateTime.now(), null, null, null, List.of(new StatusLog(null, "Pending", LocalDateTime.now())));

        traceabilityUseCase.createTraceability(traceability);

        Mockito.verify(traceabilityPersistencePort, Mockito.times(1)).createTraceability(traceability);
    }

    @Test
    @DisplayName("Updates order traceability successfully")
    void updateTraceability() {
        Long orderId = 1L;
        Traceability existingTraceability = new Traceability("jhsbdjhd", 1L, 1L, "email@email.com", LocalDateTime.now(), null, null, null, List.of(new StatusLog(null, "Pending", LocalDateTime.now())));
        Traceability updatedTraceability = new Traceability("jhsbdjhd", 1L, 1L, "email@email.com", LocalDateTime.now(), LocalDateTime.now().plusHours(1), 1L, "email@email.com", List.of(new StatusLog(null, "Pending", LocalDateTime.now()), new StatusLog("Pending", "Preparing", LocalDateTime.now())));

        Mockito.when(traceabilityPersistencePort.findTraceabilityByOrderId(orderId)).thenReturn(existingTraceability);

        traceabilityUseCase.updateTraceability(updatedTraceability, orderId);

        assertEquals(1L, existingTraceability.getEmployeeId());
        assertEquals("email@email.com", existingTraceability.getEmployeeEmail());
        assertEquals(2, existingTraceability.getStatusLogs().size());
        assertNotNull(existingTraceability.getFinalTime());
        Mockito.verify(traceabilityPersistencePort, Mockito.times(1)).updateTraceability(existingTraceability);
    }

    @Test
    @DisplayName("Returns order traceability successfully")
    void getTraceabilityByClientAndOrder() {
        Long clientId = 1L;
        Long orderId = 2L;

        Traceability traceability = new Traceability("jhsbdjhd", 2L, 1L, "email@email.com", LocalDateTime.now(), null, null, null, List.of(new StatusLog(null, "Pending", LocalDateTime.now())));

        Mockito.when(traceabilityPersistencePort.findTraceabilityByOrderId(orderId)).thenReturn(traceability);

        Traceability result = traceabilityUseCase.getTraceabilityByClientAndOrder(clientId, orderId);

        assertNotNull(result);
        assertEquals(clientId, result.getClientId());
        assertNull(result.getEmployeeEmail());
        Mockito.verify(traceabilityPersistencePort, Mockito.times(1)).findTraceabilityByOrderId(orderId);
    }

    @Test
    @DisplayName("Validation exception when traceability not found or doesn't exit")
    void getTraceabilityByClientAndOrderShouldThrowValidationExceptionWhenTraceabilityNotFound() {
        Long clientId = 1L;
        Long orderId = 2L;

        Mockito.when(traceabilityPersistencePort.findTraceabilityByOrderId(orderId)).thenReturn(null);

        TraceabilityNotFoundException exception = assertThrows(TraceabilityNotFoundException.class, () -> {
            traceabilityUseCase.getTraceabilityByClientAndOrder(clientId, orderId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.TRACEABILITY_NOT_FOUND_MESSAGE);
        Mockito.verify(traceabilityPersistencePort, Mockito.times(1)).findTraceabilityByOrderId(orderId);
    }

    @Test
    @DisplayName("Validation exception when doesn't own the order he's trying to get the traceability")
    void getTraceabilityByClientAndOrderShouldThrowValidationExceptionWhenClientDoesNotOwnOrder() {
        Long clientId = 1L;
        Long orderId = 2L;

        Traceability traceability = new Traceability("jhsbdjhd", 2L, 2L, "email@email.com", LocalDateTime.now(), null, null, null, List.of(new StatusLog(null, "Pending", LocalDateTime.now())));

        Mockito.when(traceabilityPersistencePort.findTraceabilityByOrderId(orderId)).thenReturn(traceability);

        UnauthorizedClientException exception = assertThrows(UnauthorizedClientException.class, () -> {
            traceabilityUseCase.getTraceabilityByClientAndOrder(clientId, orderId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.UNAUTHORIZED_CLIENT_MESSAGE);
        Mockito.verify(traceabilityPersistencePort, Mockito.times(1)).findTraceabilityByOrderId(orderId);
    }
}