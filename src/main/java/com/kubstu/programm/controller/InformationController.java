package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Administration;
import com.kubstu.programm.entity.Enterprise;
import com.kubstu.programm.entity.Information;
import com.kubstu.programm.exception.EnterpriseNotFoundException;
import com.kubstu.programm.exception.InformationNotFoundException;
import com.kubstu.programm.service.AdministrationService;
import com.kubstu.programm.service.EnterpriseService;
import com.kubstu.programm.service.InformationService;
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

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/informations")
public class InformationController {

    @Autowired
    private InformationService service;


    @Autowired
    private AdministrationService administrationService;

    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Information> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<Information> listInformation = page.getContent();
        System.out.println(listInformation.size());
        long startCount = (pageNum - 1) * InformationService.INFORMATION_PER_PAGE + 1;
        long endCount = startCount + InformationService.INFORMATION_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listInformation", listInformation);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "information/information";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Information information = new Information();
        List<Administration> administrationList = administrationService.getAdministrations();
        model.addAttribute("administrationList", administrationList);
        model.addAttribute("information", information);
        model.addAttribute("pageTitle", "Create New Organization");

        return "information/information_form";
    }


    @PostMapping("/save")
    public String saveUser(Information information, RedirectAttributes redirectAttributes) throws IOException {
        service.save(information);
        redirectAttributes.addFlashAttribute("message", "The information has been saved successfully");
        return getRedirectURLtoAffectedUser(information);
    }


    private String getRedirectURLtoAffectedUser(Information information) {
        String firstName = information.getName().split("@")[0];
        return "redirect:/informations/page/1?sortField=id&sortDir=asc&keyword=" + firstName;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Information information = service.getInformationById(id);
            List<Administration> administrationList = administrationService.getAdministrations();
            model.addAttribute("administrationList", administrationList);
            model.addAttribute("information", information);
            model.addAttribute("pageTitle", "Edit Information  (ID: " + id + ")");
            return "information/information_form";
        } catch (InformationNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/informations";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "The information ID "
                    + id + " has been deleted successfully");
        } catch (InformationNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/informations";
    }
}

