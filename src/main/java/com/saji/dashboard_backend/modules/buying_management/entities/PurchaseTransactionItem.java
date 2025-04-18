package com.saji.dashboard_backend.modules.buying_management.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PurchaseTransactionItem {
    @Column(nullable = false)
    private Long itemId;
    
    @Column(nullable = false)
    private Long uomId;
    
    @Column
    private double price;
    
    @Column
    private double qty;

    @Column
    private double uomFactor;

    @Column
    private double total;
}
