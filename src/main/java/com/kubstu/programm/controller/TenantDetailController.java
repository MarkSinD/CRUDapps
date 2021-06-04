package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Organization;
import com.kubstu.programm.entity.Tenant;
import com.kubstu.programm.entity.TenantDetail;
import com.kubstu.programm.exception.OrganizationNotFoundException;
import com.kubstu.programm.exception.TenantDetailNotFoundException;
import com.kubstu.programm.service.OrganizationService;
import com.kubstu.programm.service.TenantDetailService;
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
@RequestMapping("/tenantdetails")
public class TenantDetailController {

    @Autowired
    private TenantDetailService service;

    @Autowired
    private TenantService tenantService;

    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<TenantDetail> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<TenantDetail> listTenantDetail = page.getContent();
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
        model.addAttribute("listTenantDetail", listTenantDetail);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "tenant_detail/tenant_detail";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        TenantDetail tenantDetail = new TenantDetail();
        List<Tenant> listTenants = tenantService.getTenants();
        model.addAttribute("listTenants", listTenants);
        model.addAttribute("tenantDetail", tenantDetail);
        model.addAttribute("pageTitle", "Create New Tenant Detail");

        return "tenant_detail/tenant_detail_form";
    }


    @PostMapping("/save")
    public String saveUser(TenantDetail tenantDetail, RedirectAttributes redirectAttributes) throws IOException {
        service.save(tenantDetail);
        redirectAttributes.addFlashAttribute("message", "The tenant detail has been saved successfully");
        return getRedirectURLtoAffectedUser(tenantDetail);
    }


    private String getRedirectURLtoAffectedUser(TenantDetail tenantDetail) {
        String firstName = tenantDetail.getTypeRegistration().split("@")[0];
        return "redirect:/tenantdetails/page/1?sortField=id&sortDir=asc&keyword=" + firstName;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            TenantDetail tenantDetail = service.getTenantDetailById(id);
            model.addAttribute("tenantDetail", tenantDetail);
            model.addAttribute("pageTitle", "Edit Tenant Detail  (ID: " + id + ")");
            return "tenant_detail/tenant_detail_form";
        } catch (TenantDetailNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/tenantdetails";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "The tenant detail ID "
                    + id + " has been deleted successfully");
        } catch (TenantDetailNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/tenantdetails";
    }
}

