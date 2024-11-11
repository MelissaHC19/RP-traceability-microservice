package com.pragma.traceability_microservice.domain.usecase;

import com.pragma.traceability_microservice.domain.api.IRestaurantServicePort;
import com.pragma.traceability_microservice.domain.constants.ExceptionConstants;
import com.pragma.traceability_microservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.traceability_microservice.domain.exceptions.TraceabilityNotFoundException;
import com.pragma.traceability_microservice.domain.exceptions.UnauthorizedClientException;
import com.pragma.traceability_microservice.domain.exceptions.UnauthorizedOwnerException;
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

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @InjectMocks
    private TraceabilityUseCase traceabilityUseCase;

    @Test
    @DisplayName("Created order traceability successfully")
    void createTraceability() {
        Traceability traceability = new Traceability("jhsbdjhd", 1L, 1L, "email@email.com", LocalDateTime.now(), null, null, null, List.of(new StatusLog(null, "Pending", LocalDateTime.now())), 1L);

        traceabilityUseCase.createTraceability(traceability);

        Mockito.verify(traceabilityPersistencePort, Mockito.times(1)).createTraceability(traceability);
    }

    @Test
    @DisplayName("Updates order traceability successfully")
    void updateTraceability() {
        Long orderId = 1L;
        Traceability existingTraceability = new Traceability("jhsbdjhd", 1L, 1L, "email@email.com", LocalDateTime.now(), null, null, null, List.of(new StatusLog(null, "Pending", LocalDateTime.now())), 1L);
        Traceability updatedTraceability = new Traceability("jhsbdjhd", 1L, 1L, "email@email.com", LocalDateTime.now(), LocalDateTime.now().plusHours(1), 1L, "email@email.com", List.of(new StatusLog(null, "Pending", LocalDateTime.now()), new StatusLog("Pending", "Preparing", LocalDateTime.now())), 1L);

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

        Traceability traceability = new Traceability("jhsbdjhd", 2L, 1L, "email@email.com", LocalDateTime.now(), null, null, null, List.of(new StatusLog(null, "Pending", LocalDateTime.now())), 1L);

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

        Traceability traceability = new Traceability("jhsbdjhd", 2L, 2L, "email@email.com", LocalDateTime.now(), null, null, null, List.of(new StatusLog(null, "Pending", LocalDateTime.now())), 1L);

        Mockito.when(traceabilityPersistencePort.findTraceabilityByOrderId(orderId)).thenReturn(traceability);

        UnauthorizedClientException exception = assertThrows(UnauthorizedClientException.class, () -> {
            traceabilityUseCase.getTraceabilityByClientAndOrder(clientId, orderId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.UNAUTHORIZED_CLIENT_MESSAGE);
        Mockito.verify(traceabilityPersistencePort, Mockito.times(1)).findTraceabilityByOrderId(orderId);
    }

    @Test
    @DisplayName("Returns order traceability by restaurant when owner logged is the owner of the restaurant he's trying to consult")
    void listByRestaurant() {
        Long restaurantId = 1L;
        Long ownerLogged = 20L;
        Long ownerId = 20L;

        Traceability traceability1 = new Traceability("jhsbdjhd", 2L, 2L, "email@email.com", LocalDateTime.now(), null, null, null, List.of(new StatusLog(null, "Pending", LocalDateTime.now())), 1L);
        Traceability traceability2 = new Traceability("jhsbdjqw", 3L, 3L, "email@email.com", LocalDateTime.now(), null, null, null, List.of(new StatusLog(null, "Pending", LocalDateTime.now())), 1L);

        List<Traceability> expectedList = List.of(traceability1, traceability2);

        Mockito.when(restaurantServicePort.getRestaurantsOwner(restaurantId)).thenReturn(ownerId);
        Mockito.when(traceabilityPersistencePort.findTraceabilityByRestaurantId(restaurantId)).thenReturn(expectedList);

        List<Traceability> restaurantList = traceabilityUseCase.listByRestaurant(restaurantId, ownerLogged);

        assertEquals(expectedList, restaurantList);
        Mockito.verify(restaurantServicePort, Mockito.times(1)).getRestaurantsOwner(restaurantId);
        Mockito.verify(traceabilityPersistencePort, Mockito.times(1)).findTraceabilityByRestaurantId(restaurantId);
    }

    @Test
    @DisplayName("Validation exception when the owner logged is trying to consult order traceability of a restaurant that wasn't found or doesn't exists")
    void listByRestaurantShouldThrowValidationExceptionWhenRestaurantNotFound() {
        Long restaurantId = 1L;
        Long ownerLogged = 20L;
        Long ownerId = 0L;

        Mockito.when(restaurantServicePort.getRestaurantsOwner(restaurantId)).thenReturn(ownerId);

        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            traceabilityUseCase.listByRestaurant(restaurantId, ownerLogged);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.RESTAURANT_NOT_FOUND_MESSAGE);
        Mockito.verify(restaurantServicePort, Mockito.times(1)).getRestaurantsOwner(restaurantId);
        Mockito.verify(traceabilityPersistencePort, Mockito.never()).findTraceabilityByRestaurantId(restaurantId);
    }

    @Test
    @DisplayName("Validation exception when the owner logged is trying to consult order traceability of a restaurant he doesn't owns")
    void listByRestaurantShouldThrowValidationExceptionWhenOwnerDoesNotOwnRestaurant() {
        Long restaurantId = 1L;
        Long ownerLogged = 20L;
        Long ownerId = 30L;

        Mockito.when(restaurantServicePort.getRestaurantsOwner(restaurantId)).thenReturn(ownerId);

        UnauthorizedOwnerException exception = assertThrows(UnauthorizedOwnerException.class, () -> {
            traceabilityUseCase.listByRestaurant(restaurantId, ownerLogged);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.UNAUTHORIZED_OWNER_MESSAGE);
        Mockito.verify(restaurantServicePort, Mockito.times(1)).getRestaurantsOwner(restaurantId);
        Mockito.verify(traceabilityPersistencePort, Mockito.never()).findTraceabilityByRestaurantId(restaurantId);
    }

    @Test
    @DisplayName("Returns employee ranking when employee has orders tracked")
    void listEmployeeRanking() {
        Long restaurantId = 1L;
        Long ownerLogged = 20L;
        Long ownerId = 20L;
        Long employeeId = 55L;

        Traceability traceability1 = new Traceability("jhsbdjhd", 2L, 2L, "email@email.com", LocalDateTime.now(), LocalDateTime.now().plusHours(2), 55L, null, List.of(new StatusLog(null, "Pending", LocalDateTime.now())), 1L);
        Traceability traceability2 = new Traceability("jhsbdjqw", 3L, 3L, "email@email.com", LocalDateTime.now(), LocalDateTime.now().plusHours(1), 55L, null, List.of(new StatusLog("Pending", "Preparing", LocalDateTime.now())), 1L);

        List<Traceability> restaurantList = List.of(traceability1, traceability2);

        Mockito.when(restaurantServicePort.getRestaurantsOwner(restaurantId)).thenReturn(ownerId);
        Mockito.when(traceabilityPersistencePort.findTraceabilityByRestaurantId(restaurantId)).thenReturn(restaurantList);

        List<Traceability> employeeRanking = traceabilityUseCase.listEmployeeRanking(restaurantId, ownerLogged, employeeId);

        assertEquals(2, employeeRanking.size());
        assertEquals(traceability2, employeeRanking.get(0));
        assertEquals(traceability1, employeeRanking.get(1));
        Mockito.verify(restaurantServicePort, Mockito.times(1)).getRestaurantsOwner(restaurantId);
        Mockito.verify(traceabilityPersistencePort, Mockito.times(1)).findTraceabilityByRestaurantId(restaurantId);
    }

    @Test
    @DisplayName("Returns empty list if restaurant doesn't have order traceability")
    void listEmployeeRankingShouldReturnEmptyListWhenRestaurantDoesNotHaveTraceability() {
        Long restaurantId = 1L;
        Long ownerLogged = 20L;
        Long ownerId = 20L;
        Long employeeId = 55L;

        Mockito.when(restaurantServicePort.getRestaurantsOwner(restaurantId)).thenReturn(ownerId);
        Mockito.when(traceabilityPersistencePort.findTraceabilityByRestaurantId(restaurantId)).thenReturn(List.of());

        List<Traceability> employeeRanking = traceabilityUseCase.listEmployeeRanking(restaurantId, ownerLogged, employeeId);

        assertTrue(employeeRanking.isEmpty());
        Mockito.verify(restaurantServicePort, Mockito.times(1)).getRestaurantsOwner(restaurantId);
        Mockito.verify(traceabilityPersistencePort, Mockito.times(1)).findTraceabilityByRestaurantId(restaurantId);
    }

    @Test
    @DisplayName("Returns empty list when employee doesn't have orders tracked")
    void listEmployeeRankingShouldReturnEmptyListWhenEmployeeDoesNotHaveOrdersTracked() {
        Long restaurantId = 1L;
        Long ownerLogged = 20L;
        Long ownerId = 20L;
        Long employeeId = 55L;

        Traceability traceability1 = new Traceability("jhsbdjhd", 2L, 2L, "email@email.com", LocalDateTime.now(), LocalDateTime.now().plusHours(2), 45L, null, List.of(new StatusLog(null, "Pending", LocalDateTime.now())), 1L);

        List<Traceability> restaurantList = List.of(traceability1);

        Mockito.when(restaurantServicePort.getRestaurantsOwner(restaurantId)).thenReturn(ownerId);
        Mockito.when(traceabilityPersistencePort.findTraceabilityByRestaurantId(restaurantId)).thenReturn(restaurantList);

        List<Traceability> employeeRanking = traceabilityUseCase.listEmployeeRanking(restaurantId, ownerLogged, employeeId);

        assertTrue(employeeRanking.isEmpty());
        Mockito.verify(restaurantServicePort, Mockito.times(1)).getRestaurantsOwner(restaurantId);
        Mockito.verify(traceabilityPersistencePort, Mockito.times(1)).findTraceabilityByRestaurantId(restaurantId);
    }
}