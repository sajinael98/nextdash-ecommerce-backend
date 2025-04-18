package com.saji.dashboard_backend.modules.location_management.entites;

import org.hibernate.annotations.Formula;

import com.saji.dashboard_backend.shared.entites.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "locations")
public class Location extends BaseEntity {
    @Column(nullable = false)
    private long countryId;

    @Formula("(SELECT c.title FROM res_countries c WHERE c.id = countryId)")
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String title;

    @PrePersist
    @PreUpdate
    public void updateTitle(){
        title = address + ", " + city + ", " + country;
    }
}
