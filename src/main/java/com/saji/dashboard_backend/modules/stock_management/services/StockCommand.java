package com.saji.dashboard_backend.modules.stock_management.services;

public interface StockCommand {
    void execute();

    void undo();
}
