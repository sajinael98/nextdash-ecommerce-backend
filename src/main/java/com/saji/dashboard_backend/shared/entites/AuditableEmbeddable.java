package com.saji.dashboard_backend.shared.entites;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public abstract class AuditableEmbeddable {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void markCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}