package com.saji.dashboard_backend.modules.stock_management.entities;

import com.saji.dashboard_backend.shared.entites.BaseResource;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "uoms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Uom extends BaseResource {
    @Column(nullable = false)
    private String uom;
}
