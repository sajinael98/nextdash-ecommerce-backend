package com.saji.dashboard_backend.modules.location_management.entites;

import com.saji.dashboard_backend.shared.entites.BaseResource;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "countries")
public class Country extends BaseResource {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String code;
}
