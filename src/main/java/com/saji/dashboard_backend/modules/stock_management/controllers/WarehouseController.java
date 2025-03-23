package com.saji.dashboard_backend.modules.stock_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.stock_management.entities.Warehouse;
import com.saji.dashboard_backend.modules.stock_management.services.WarehouseService;
import com.saji.dashboard_backend.shared.controllers.BaseController;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController extends BaseController<Warehouse> {

    public WarehouseController(WarehouseService service) {
        super(service);
    }

}
