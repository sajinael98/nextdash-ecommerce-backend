package com.saji.dashboard_backend.modules.stock_management.entities;

import java.util.HashSet;
import java.util.Set;

import com.saji.dashboard_backend.shared.entites.BaseResource;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
@Table(name = "variants")
public class Variant extends BaseResource{
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "variant_values", joinColumns = @JoinColumn(name = "variantId", nullable = false))
    private Set<VariantValue> values = new HashSet<>();
}
