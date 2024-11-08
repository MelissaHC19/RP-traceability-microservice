package com.pragma.traceability_microservice.domain.model;

import java.time.LocalDateTime;

public class StatusLog {
    private String lastStatus;
    private String newStatus;
    private LocalDateTime issuedAt;

    public StatusLog(String lastStatus, String newStatus, LocalDateTime issuedAt) {
        this.lastStatus = lastStatus;
        this.newStatus = newStatus;
        this.issuedAt = issuedAt;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }
}
