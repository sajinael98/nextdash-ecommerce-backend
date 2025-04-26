package com.saji.dashboard_backend.modules.stock_management.services;

import com.saji.dashboard_backend.modules.stock_management.entities.StockRoom;

public interface StockMovementStrategy {
    void apply(StockRoom stockRoom, Double quantity);
}
