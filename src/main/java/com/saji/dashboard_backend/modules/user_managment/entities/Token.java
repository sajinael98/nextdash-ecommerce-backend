package com.saji.dashboard_backend.modules.user_managment.entities;

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
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    @Column(name = "expired", columnDefinition = "INT")
    private boolean expired;
    
    @Column(name = "revoked", columnDefinition = "INT")
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}