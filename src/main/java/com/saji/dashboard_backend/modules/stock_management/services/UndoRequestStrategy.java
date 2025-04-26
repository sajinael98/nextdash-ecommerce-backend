package com.saji.dashboard_backend.modules.stock_management.services;

import com.saji.dashboard_backend.modules.stock_management.entities.StockRoom;

public class UndoRequestStrategy implements StockMovementStrategy {

    @Override
    public void apply(StockRoom stockRoom, Double quantity) {
        var currentQty = stockRoom.getOrderedQty();
        stockRoom.setOrderedQty(currentQty - quantity);
    }
    
}
