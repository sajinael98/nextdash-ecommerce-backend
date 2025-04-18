package com.saji.dashboard_backend.modules.stock_management.entities;

import java.util.HashSet;
import java.util.Set;

import com.saji.dashboard_backend.modules.buying_management.entities.PurchaseTransactionType;
import com.saji.dashboard_backend.shared.entites.BaseEntity;

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

@Entity
@Table(name = "stock_rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRoom extends BaseEntity {

    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private Long uomId;

    @Column(nullable = false)
    private Long warehouseId;

    @Column(nullable = false)
    private Double currentQty = 0.0;

    @Column(nullable = false)
    private Double orderedQty = 0.0;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "stock_room_logs", joinColumns = @JoinColumn(name = "stockRoomId", nullable = false))
    private Set<StockRoomLog> logs = new HashSet<>();

    public void addLog(StockRoomLog log) {
        validateLog(log);
        logs.add(log);
        updateQuantities(log, log.getTransactionType() != PurchaseTransactionType.SOLD);
    }

    public void removeLog(Long id) {
        StockRoomLog logToRemove = logs.stream()
                .filter(log -> log.getTransactionId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Log with transaction ID " + id + " not found."));

        logs.remove(logToRemove);
        updateQuantities(logToRemove, logToRemove.getTransactionType() == PurchaseTransactionType.SOLD);    
    }

    private void validateLog(StockRoomLog log) {
        if (log.getQty() == null || log.getQty() <= 0) {
            throw new IllegalArgumentException("Quantity cannot be null, less than, or equal to zero.");
        }
    }

    private void updateQuantities(StockRoomLog log, boolean isAdding) {
        Double quantityChange = isAdding ? log.getQty() : -log.getQty();

        switch (log.getTransactionType()) {
            case RECEIVED:
                currentQty += quantityChange;
                break;
            case SOLD:
                currentQty += quantityChange;
                break;
            case ORDERED:
                orderedQty += quantityChange;
                break;
            default:
                throw new IllegalArgumentException("Unsupported transaction type: " + log.getTransactionType());
        }
    }
}