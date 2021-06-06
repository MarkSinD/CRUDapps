package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Administration;
import com.kubstu.programm.entity.Enterprise;
import com.kubstu.programm.exception.EnterpriseNotFoundException;
import com.kubstu.programm.service.AdministrationService;
import com.kubstu.programm.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/enterprises")
public class EnterpriseController {

    @Autowired
    private EnterpriseService service;

    @Autowired
    private AdministrationService administrationService;

    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }


    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Enterprise> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<Enterprise> listEnterprise = page.getContent();
        long startCount = (pageNum - 1) * EnterpriseService.ENTERPRICE_PER_PAGE + 1;
        long endCount = startCount + EnterpriseService.ENTERPRICE_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listEnterprise", listEnterprise);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "enterprise/enterprise";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Enterprise enterprise = new Enterprise();
        List<Administration> administrationList = administrationService.getAdministrations();

        model.addAttribute("administrationList", administrationList);
        model.addAttribute("enterprise", enterprise);
        model.addAttribute("pageTitle", "Create New Enterprise");

        return "enterprise/enterprise_form";
    }


    @PostMapping("/save")
    public String saveUser(Enterprise enterprise, RedirectAttributes redirectAttributes) throws IOException {
        service.save(enterprise);
        redirectAttributes.addFlashAttribute("message", "The enterprise has been saved successfully");
        return getRedirectURLtoAffectedUser(enterprise);
    }


    private String getRedirectURLtoAffectedUser(Enterprise enterprise) {
        String firstName = enterprise.getName().split("@")[0];
        return "redirect:/enterprises/page/1?sortField=id&sortDir=asc&keyword=" + firstName;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Enterprise enterprise = service.getEnterpriseById(id);
            List<Administration> administrationList = administrationService.getAdministrations();

            model.addAttribute("administrationList", administrationList);
            model.addAttribute("enterprise", enterprise);
            model.addAttribute("pageTitle", "Edit Enterprise  (ID: " + id + ")");
            return "enterprise/enterprise_form";
        } catch (EnterpriseNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/enterprises";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "The enterprise ID "
                    + id + " has been deleted successfully");
        } catch (EnterpriseNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/enterprises";
    }
}

