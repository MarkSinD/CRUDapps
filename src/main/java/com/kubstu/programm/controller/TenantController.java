package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Flat;
import com.kubstu.programm.entity.House;
import com.kubstu.programm.entity.Organization;
import com.kubstu.programm.entity.Tenant;
import com.kubstu.programm.exception.OrganizationNotFoundException;
import com.kubstu.programm.exception.TenantNotFoundException;
import com.kubstu.programm.service.FlatService;
import com.kubstu.programm.service.HouseService;
import com.kubstu.programm.service.OrganizationService;
import com.kubstu.programm.service.TenantService;
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
@RequestMapping("/tenants")
public class TenantController {

    @Autowired
    private TenantService service;

    @Autowired
    private FlatService flatService;

    @Autowired
    private HouseService houseService;

    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Tenant> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<Tenant> listTenant = page.getContent();
        long startCount = (pageNum - 1) * TenantService.TENANT_PER_PAGE + 1;
        long endCount = startCount + TenantService.TENANT_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listTenant", listTenant);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "tenant/tenant";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Tenant tenant = new Tenant();
        List<Flat> listFlats = flatService.getFlats();
        List<House> listHouses = houseService.getHouses();

        model.addAttribute("listFlats", listFlats);
        model.addAttribute("listHouses", listHouses);
        model.addAttribute("tenant", tenant);
        model.addAttribute("pageTitle", "Create New Tenant");

        return "tenant/tenant_form";
    }

    @PostMapping("/save")
    public String saveUser(Tenant tenant, RedirectAttributes redirectAttributes) throws IOException {
        service.save(tenant);
        redirectAttributes.addFlashAttribute("message", "The tenant has been saved successfully");
        return getRedirectURLtoAffectedUser(tenant);
    }


    private String getRedirectURLtoAffectedUser(Tenant tenant) {
        String firstName = tenant.getName().split("@")[0];
        return "redirect:/tenants/page/1?sortField=id&sortDir=asc&keyword=" + firstName;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Tenant tenant = service.getTenantById(id);
            model.addAttribute("tenant", tenant);
            model.addAttribute("pageTitle", "Edit Tenant  (ID: " + id + ")");
            return "tenant/tenant_form";
        } catch (TenantNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/tenants";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "The tenant ID "
                    + id + " has been deleted successfully");
        } catch (TenantNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/tenants";
    }
}

