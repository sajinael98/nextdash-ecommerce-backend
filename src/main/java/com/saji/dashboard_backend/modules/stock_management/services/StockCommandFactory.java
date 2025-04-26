package com.saji.dashboard_backend.modules.stock_management.services;

import com.saji.dashboard_backend.modules.buying_management.entities.PurchaseTransactionType;
import com.saji.dashboard_backend.modules.stock_management.entities.StockRoom;

public class StockCommandFactory {

    public static StockCommand createCommand(PurchaseTransactionType type, StockRoom stockRoom, double quantity) {
        return switch (type) {
            case PURCHASE -> new StockMovementCommand(stockRoom, quantity,
                    new PurchaseStrategy(), new UndoPurchaseStrategy());
            case RETURN -> new StockMovementCommand(stockRoom, quantity,
                    new ReturnStrategy(), new UndoReturnStrategy());
            case REQUEST -> new StockMovementCommand(stockRoom, quantity,
                    new RequestStrategy(), new UndoRequestStrategy());
        };
    }
}
