package com.saji.dashboard_backend.modules.stock_management.services;

import com.saji.dashboard_backend.modules.stock_management.entities.StockRoom;

public class StockMovementCommand implements StockCommand {
    private final StockRoom stockRoom;
    private final double quantity;
    private final StockMovementStrategy strategy;
    private final StockMovementStrategy undoStrategy;

    public StockMovementCommand(StockRoom stockRoom, double quantity,
            StockMovementStrategy strategy, StockMovementStrategy undoStrategy) {
        this.stockRoom = stockRoom;
        this.quantity = quantity;
        this.strategy = strategy;
        this.undoStrategy = undoStrategy;
    }

    public void execute() {
        strategy.apply(stockRoom, quantity);
    }

    public void undo() {
        undoStrategy.apply(stockRoom, quantity);
    }
}
