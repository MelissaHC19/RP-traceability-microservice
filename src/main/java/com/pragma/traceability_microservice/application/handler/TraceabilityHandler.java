package com.pragma.traceability_microservice.application.handler;

import com.pragma.traceability_microservice.application.dto.request.CreateTraceabilityRequest;
import com.pragma.traceability_microservice.application.dto.request.UpdateTraceabilityRequest;
import com.pragma.traceability_microservice.application.dto.response.GetTraceabilityResponse;
import com.pragma.traceability_microservice.application.dto.response.ListByRestaurantResponse;
import com.pragma.traceability_microservice.application.mapper.ICreateTraceabilityRequestMapper;
import com.pragma.traceability_microservice.application.mapper.IGetTraceabilityResponseMapper;
import com.pragma.traceability_microservice.application.mapper.IListByRestaurantResponseMapper;
import com.pragma.traceability_microservice.application.mapper.IUpdateTraceabilityRequestMapper;
import com.pragma.traceability_microservice.domain.api.ITraceabilityServicePort;
import com.pragma.traceability_microservice.domain.model.Traceability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TraceabilityHandler implements ITraceabilityHandler {
    private final ITraceabilityServicePort traceabilityServicePort;
    private final ICreateTraceabilityRequestMapper createTraceabilityRequestMapper;
    private final IUpdateTraceabilityRequestMapper updateTraceabilityRequestMapper;
    private final IGetTraceabilityResponseMapper getTraceabilityResponseMapper;
    private final IListByRestaurantResponseMapper listByRestaurantResponseMapper;

    @Override
    public void createTraceability(CreateTraceabilityRequest createTraceabilityRequest) {
        Traceability traceability = createTraceabilityRequestMapper.createRequestToTraceability(createTraceabilityRequest);
        traceabilityServicePort.createTraceability(traceability);
    }

    @Override
    public void updateTraceability(UpdateTraceabilityRequest updateTraceabilityRequest, Long orderId) {
        Traceability traceability = updateTraceabilityRequestMapper.updateRequestToTraceability(updateTraceabilityRequest);
        traceabilityServicePort.updateTraceability(traceability, orderId);
    }

    @Override
    public GetTraceabilityResponse getTraceabilityByClientAndOrder(Long clientId, Long orderId) {
        Traceability traceability = traceabilityServicePort.getTraceabilityByClientAndOrder(clientId, orderId);
        return getTraceabilityResponseMapper.traceabilityToResponse(traceability);
    }

    @Override
    public List<ListByRestaurantResponse> listByRestaurant(Long restaurantId, Long ownerLogged) {
        List<Traceability> traceabilityList = traceabilityServicePort.listByRestaurant(restaurantId, ownerLogged);
        return listByRestaurantResponseMapper.traceabilityToResponse(traceabilityList);
    }

    @Override
    public List<ListByRestaurantResponse> listEmployeeRanking(Long restaurantId, Long ownerLogged, Long employeeId) {
        List<Traceability> traceabilityList = traceabilityServicePort.listEmployeeRanking(restaurantId, ownerLogged, employeeId);
        return listByRestaurantResponseMapper.traceabilityToResponse(traceabilityList);
    }
}
