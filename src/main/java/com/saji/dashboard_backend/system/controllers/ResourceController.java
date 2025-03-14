package com.saji.dashboard_backend.system.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.user_managment.entities.Permission;
import com.saji.dashboard_backend.system.services.ResourceService;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    @Autowired
    private ResourceService service;

    @GetMapping
    public ResponseEntity<List<String>> getResourcesList(){
        return ResponseEntity.ok().body(service.getResources());
    }

    @GetMapping("/{id}/permissions")
    public ResponseEntity<List<Permission>> getResourcePermissions(@PathVariable String resource){
        return ResponseEntity.ok().body(service.getResourcePermissions(resource));
    }
    
    @PatchMapping("/{resource}/{id}")
    public void updateValue(@PathVariable(required = true) String resource, @PathVariable Long id, @RequestBody UpdateResourceValue updateResourceValue){
        service.updateValue(resource, id, updateResourceValue.column(), updateResourceValue.value());
    }
}


record UpdateResourceValue(String column, Object value){

}