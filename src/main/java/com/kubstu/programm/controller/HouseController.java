package com.kubstu.programm.controller;

import com.kubstu.programm.entity.House;
import com.kubstu.programm.entity.Organization;
import com.kubstu.programm.exception.HouseNotFoundException;
import com.kubstu.programm.exception.OrganizationNotFoundException;
import com.kubstu.programm.service.HouseService;
import com.kubstu.programm.service.OrganizationService;
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
@RequestMapping("/houses")
public class HouseController {

    @Autowired
    private HouseService service;

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<House> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<House> listHouse = page.getContent();
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
        model.addAttribute("listHouse", listHouse);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "house/house";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        House house = new House();
        List<Organization> listOrganization = organizationService.getOrganizations();

        model.addAttribute("listOrganization", listOrganization);
        model.addAttribute("house", house);
        model.addAttribute("pageTitle", "Create New House");

        return "house/house_form";
    }


    @PostMapping("/save")
    public String saveUser(House house, RedirectAttributes redirectAttributes) throws IOException {
        service.save(house);
        redirectAttributes.addFlashAttribute("message", "The house has been saved successfully");
        return getRedirectURLtoAffectedUser(house);
    }


    private String getRedirectURLtoAffectedUser(House house) {
        String firstName = house.getAddress().split("@")[0];
        return "redirect:/houses/page/1?sortField=id&sortDir=asc&keyword=" + firstName;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){


        try {
            House house = service.getHouseById(id);
            model.addAttribute("house", house);
            model.addAttribute("pageTitle", "Edit House  (ID: " + id + ")");
            return "house/house_form";
        } catch (HouseNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/houses";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "The house ID "
                    + id + " has been deleted successfully");
        } catch (HouseNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/houses";
    }
}

