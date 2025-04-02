package com.saji.dashboard_backend.modules.stock_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.stock_management.entities.Uom;
import com.saji.dashboard_backend.modules.stock_management.services.UomService;
import com.saji.dashboard_backend.shared.controllers.BaseController;

@RestController
@RequestMapping("/uoms")
public class UomController extends BaseController<Uom> {

    public UomController(UomService service) {
        super(service);
    }
    
}
