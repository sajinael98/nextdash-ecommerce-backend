package com.saji.dashboard_backend.modules.stock_management.entities;

import java.util.HashSet;
import java.util.Set;

import com.saji.dashboard_backend.shared.entites.BaseResource;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item extends BaseResource {
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "item_uoms", joinColumns = @JoinColumn(name = "itemId", nullable = false))
    private Set<ItemUom> uoms = new HashSet<>();
}
