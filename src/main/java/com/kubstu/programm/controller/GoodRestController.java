package com.kubstu.programm.controller;

import com.kubstu.programm.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GoodRestController {
    @Autowired
    private GoodService service;

    @PostMapping("/goods/check_email")
    public String checkDulicateEmail(@Param("id") Integer id, @Param("name")String name){

        return service.isNameUnique(id, name) ? "OK" : "OK";
    }
}