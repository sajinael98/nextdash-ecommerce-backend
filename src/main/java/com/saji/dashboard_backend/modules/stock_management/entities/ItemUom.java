package com.saji.dashboard_backend.modules.stock_management.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ItemUom {
    @Column(nullable = false)
    private Long uomId;

    @Column
    private String uom;

    @Column
    private Double value;
}
