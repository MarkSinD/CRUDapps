package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Organization;
import com.kubstu.programm.exception.OrganizationNotFoundException;
import com.kubstu.programm.service.OrganizationService;
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
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService service;

    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Organization> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<Organization> listOrganization = page.getContent();
        long startCount = (pageNum - 1) * OrganizationService.ORGANIZATION_PER_PAGE + 1;
        long endCount = startCount + OrganizationService.ORGANIZATION_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listOrganization", listOrganization);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "organization/organization";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Organization organization = new Organization();
        model.addAttribute("organization", organization);
        model.addAttribute("pageTitle", "Create New Organization");

        return "organization/organization_form";
    }


    @PostMapping("/save")
    public String saveUser(Organization organization, RedirectAttributes redirectAttributes) throws IOException {
        service.save(organization);
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully");
        return getRedirectURLtoAffectedUser(organization);
    }


    private String getRedirectURLtoAffectedUser(Organization organization) {
        String firstName = organization.getName().split("@")[0];
        return "redirect:/organizations/page/1?sortField=id&sortDir=asc&keyword=" + firstName;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Organization organization = service.getOrganizationById(id);
            model.addAttribute("organization", organization);
            model.addAttribute("pageTitle", "Edit User  (ID: " + id + ")");
            return "organization/organization_form";
        } catch (OrganizationNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/organizations";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "The organization ID "
                    + id + " has been deleted successfully");
        } catch (OrganizationNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/organizations";
    }
}

