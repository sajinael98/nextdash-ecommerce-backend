package com.saji.dashboard_backend.modules.stock_management.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ItemVariant {
    @Column(nullable = false)
    private Long variantId;

    @Column
    private String value;
}
