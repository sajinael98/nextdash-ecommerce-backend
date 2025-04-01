package com.saji.dashboard_backend.modules.stock_management.entities;

import com.saji.dashboard_backend.shared.entites.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item extends BaseEntity {
    @Column(columnDefinition = "INT")
    private boolean enabled;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Embedded
    private ItemVariantInformation variantInformation = new ItemVariantInformation();
    
    @Embedded
    private ItemWebsiteInformation websiteInformation = new ItemWebsiteInformation();
}
