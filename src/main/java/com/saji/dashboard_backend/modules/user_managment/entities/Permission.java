package com.saji.dashboard_backend.modules.user_managment.entities;

import com.saji.dashboard_backend.shared.entites.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permissions")
@ToString(exclude = "role")
public class Permission extends BaseEntity{
    @Column(nullable = false)
    private String entity;
    
    @Column(columnDefinition = "INT")
    private boolean createR;

    @Column(columnDefinition = "INT")
    private boolean readR;

    @Column(columnDefinition = "INT")
    private boolean editR;

    @Column(columnDefinition = "INT")
    private boolean deleteR;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
}
