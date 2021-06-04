package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Tenant;
import com.kubstu.programm.service.OrganizationService;
import com.kubstu.programm.service.TenantDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TenantDetailRestController {
    @Autowired
    private TenantDetailService service;

    @PostMapping("/tenantdetail/check_email")
    public String checkDulicateEmail(@Param("id") Integer id, @Param("tenant") Tenant tenant){

        String fullname = tenant.getName() + ' ' + tenant.getSurname();

        return service.isNameUnique(id, fullname) ? "OK" : "Duplicated";
    }
}
