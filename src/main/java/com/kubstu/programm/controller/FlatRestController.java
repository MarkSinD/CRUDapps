package com.kubstu.programm.controller;

import com.kubstu.programm.service.FlatService;
import com.kubstu.programm.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlatRestController {
    @Autowired
    private FlatService service;

    @PostMapping("/flats/check_email")
    public String checkDulicateEmail(@Param("id") Integer id, @Param("name")String name){

        return service.isNameUnique(id, name) ? "OK" : "Duplicated";
    }
}
