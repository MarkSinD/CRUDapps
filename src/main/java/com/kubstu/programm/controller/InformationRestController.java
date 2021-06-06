package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Information;
import com.kubstu.programm.service.EnterpriseService;
import com.kubstu.programm.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InformationRestController {
    @Autowired
    private InformationService service;

    @PostMapping("/informations/check_email")
    public String checkDulicateEmail(@Param("id") Integer id, @Param("name")String name){

        return service.isNameUnique(id, name) ? "OK" : "Duplicated";
    }
}