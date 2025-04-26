package com.saji.dashboard_backend.modules.stock_management.entities;

import java.time.LocalDateTime;

import com.saji.dashboard_backend.modules.buying_management.entities.PurchaseTransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class StockRoomLog {
    @Column
    private Long transactionId;
    
    @Column
    private LocalDateTime transactionDate;

    @Column
    @Enumerated(EnumType.STRING)
    private PurchaseTransactionType transactionType;
    
    @Column
    private String transactionVoucherType;

    @Column
    private Double qty;
}
