package com.saji.dashboard_backend.modules.stock_management.entities;

import java.util.HashSet;
import java.util.Set;

import com.saji.dashboard_backend.modules.buying_management.entities.PurchaseTransactionType;
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

@Entity
@Table(name = "stock_rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRoom extends BaseResource {

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
}