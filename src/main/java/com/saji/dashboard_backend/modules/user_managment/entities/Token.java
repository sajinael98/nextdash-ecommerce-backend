package com.saji.dashboard_backend.modules.user_managment.entities;

import com.saji.dashboard_backend.shared.entites.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token extends BaseEntity {
    private String token;
    @Column(columnDefinition = "INT")
    private boolean expired;

    @Column(columnDefinition = "INT")
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}