package com.saji.dashboard_backend.modules.stock_management.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class VariantValue{
    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private String value;
}
