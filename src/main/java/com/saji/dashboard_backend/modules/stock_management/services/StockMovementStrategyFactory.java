package com.saji.dashboard_backend.modules.stock_management.services;

import com.saji.dashboard_backend.modules.buying_management.entities.PurchaseTransactionType;

public class StockMovementStrategyFactory {
    public static StockMovementStrategy getStrategy(PurchaseTransactionType type) {
        return switch (type) {
            case PURCHASE -> new PurchaseStrategy();
            case RETURN -> new ReturnStrategy();
            case REQUEST -> new RequestStrategy();
        };
    }
}