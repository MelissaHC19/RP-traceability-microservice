package com.pragma.traceability_microservice.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Traceability {
    private String id;
    private Long orderId;
    private Long clientId;
    private String clientEmail;
    private LocalDateTime initialTime;
    private LocalDateTime finalTime;
    private Long employeeId;
    private String employeeEmail;
    private List<StatusLog> statusLogs;
    private Long restaurantId;

    public Traceability(String id, Long orderId, Long clientId, String clientEmail, LocalDateTime initialTime, LocalDateTime finalTime, Long employeeId, String employeeEmail, List<StatusLog> statusLogs, Long restaurantId) {
        this.id = id;
        this.orderId = orderId;
        this.clientId = clientId;
        this.clientEmail = clientEmail;
        this.initialTime = initialTime;
        this.finalTime = finalTime;
        this.employeeId = employeeId;
        this.employeeEmail = employeeEmail;
        this.statusLogs = statusLogs;
        this.restaurantId = restaurantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public LocalDateTime getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(LocalDateTime initialTime) {
        this.initialTime = initialTime;
    }

    public LocalDateTime getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(LocalDateTime finalTime) {
        this.finalTime = finalTime;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public List<StatusLog> getStatusLogs() {
        return statusLogs;
    }

    public void setStatusLogs(List<StatusLog> statusLogs) {
        this.statusLogs = statusLogs;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
