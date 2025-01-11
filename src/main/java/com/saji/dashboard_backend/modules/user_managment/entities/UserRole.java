package com.saji.dashboard_backend.modules.user_managment.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class UserRole {
    @Column(nullable = false)
    private String role;
}
