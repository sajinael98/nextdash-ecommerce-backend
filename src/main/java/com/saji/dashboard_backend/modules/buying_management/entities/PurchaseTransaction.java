package com.saji.dashboard_backend.modules.buying_management.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.saji.dashboard_backend.modules.stock_management.entities.ItemUom;
import com.saji.dashboard_backend.shared.entites.BaseResource;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseTransaction extends BaseResource {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private PurchaseTransactionType transactionType;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime transactionDate;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Long warehouseId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "purchase_transaction_items", joinColumns = @JoinColumn(name = "voucherId", nullable = false))
    @NotEmpty(message = "At least one item is required in the purchase transaction")
    @Valid
    private Set<PurchaseTransactionItem> items = new HashSet<>();

    @Column
    @Min(1)
    private Double total;
}
