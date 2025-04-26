package com.saji.dashboard_backend.modules.stock_management.services;

import com.saji.dashboard_backend.modules.stock_management.entities.StockRoom;

public class UndoReturnStrategy implements StockMovementStrategy {
    @Override
    public void apply(StockRoom stockRoom, Double quantity) {
        var currentQty = stockRoom.getCurrentQty();
        stockRoom.setCurrentQty(quantity + currentQty);
    }
}
