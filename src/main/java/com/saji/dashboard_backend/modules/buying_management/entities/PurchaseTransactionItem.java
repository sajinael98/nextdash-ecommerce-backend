package com.saji.dashboard_backend.modules.buying_management.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class PurchaseTransactionItem {
    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Long itemId;
    
    @Column(nullable = false)
    @NotBlank
    private String item;
    
    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Long uomId;

    @Column(nullable = false)
    @NotBlank
    private String uom;
    
    @Column
    @NotNull
    private Double uomFactor = 0.0;
    
    @Column
    @NotNull
    private Double qty = 0.0;
    
    @Column
    @NotNull
    private Double stockQty = 0.0;
    
    @Column
    @NotNull
    private Double price = 0.0;
    
    @Column
    private Double total;
    
}
