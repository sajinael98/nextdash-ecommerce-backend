package com.saji.dashboard_backend.modules.stock_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.stock_management.entities.Variant;
import com.saji.dashboard_backend.modules.stock_management.services.VariantService;
import com.saji.dashboard_backend.shared.controllers.BaseController;

@RestController
@RequestMapping("/variants")
public class VariantController extends BaseController<Variant> {

    public VariantController(VariantService service) {
        super(service);
    }
    
}
