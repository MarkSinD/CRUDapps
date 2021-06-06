package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Administration;
import com.kubstu.programm.entity.Enterprise;
import com.kubstu.programm.entity.Executive;
import com.kubstu.programm.exception.EnterpriseNotFoundException;
import com.kubstu.programm.exception.ExecutiveNotFoundException;
import com.kubstu.programm.service.AdministrationService;
import com.kubstu.programm.service.EnterpriseService;
import com.kubstu.programm.service.ExecutiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/executives")
public class ExecutiveController {

    @Autowired
    private ExecutiveService service;

    @Autowired
    private AdministrationService administrationService;

    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Executive> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<Executive> listExecutive = page.getContent();
        long startCount = (pageNum - 1) * ExecutiveService.EXECUTIVE_PER_PAGE + 1;
        long endCount = startCount + ExecutiveService.EXECUTIVE_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listExecutive", listExecutive);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "executive/executive";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Executive executive = new Executive();
        List<Administration> administrationList = administrationService.getAdministrations();

        model.addAttribute("administrationList", administrationList);
        model.addAttribute("executive", executive);
        model.addAttribute("pageTitle", "Create New Executive");

        return "executive/executive_form";
    }


    @PostMapping("/save")
    public String saveUser(Executive executive, RedirectAttributes redirectAttributes) throws IOException {
        service.save(executive);
        redirectAttributes.addFlashAttribute("message", "The executive has been saved successfully");
        return getRedirectURLtoAffectedUser(executive);
    }


    private String getRedirectURLtoAffectedUser(Executive executive) {
        String firstName = executive.getName().split("@")[0];
        return "redirect:/executives/page/1?sortField=id&sortDir=asc&keyword=" + firstName;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Executive executive = service.getExecutiveById(id);
            List<Administration> administrationList = administrationService.getAdministrations();

            model.addAttribute("administrationList", administrationList);
            model.addAttribute("executive", executive);
            model.addAttribute("pageTitle", "Edit executive  (ID: " + id + ")");
            return "executive/executive_form";
        } catch (ExecutiveNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/executives";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "The executive ID "
                    + id + " has been deleted successfully");
        } catch (ExecutiveNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/executive";
    }
}

