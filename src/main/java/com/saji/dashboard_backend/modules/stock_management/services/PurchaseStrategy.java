package com.saji.dashboard_backend.modules.stock_management.services;

import com.saji.dashboard_backend.modules.stock_management.entities.StockRoom;

public class PurchaseStrategy implements StockMovementStrategy {
    public void apply(StockRoom stockRoom, Double quantity) {
        var currentQty = stockRoom.getCurrentQty();
        stockRoom.setCurrentQty(currentQty + quantity);
    }
}
