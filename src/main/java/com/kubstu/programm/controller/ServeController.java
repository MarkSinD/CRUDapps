package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Organization;
import com.kubstu.programm.entity.Serve;
import com.kubstu.programm.entity.Tenant;
import com.kubstu.programm.exception.OrganizationNotFoundException;
import com.kubstu.programm.exception.ServeNotFoundException;
import com.kubstu.programm.service.OrganizationService;
import com.kubstu.programm.service.ServeService;
import com.kubstu.programm.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/serves")
public class ServeController {

    @Autowired
    private ServeService service;

    @Autowired
    private TenantService tenantService;

    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Serve> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<Serve> listServe = page.getContent();
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
        model.addAttribute("listServe", listServe);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "serve/serve";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Serve serve = new Serve();
        List<Tenant> listTenants = tenantService.getTenants();
        model.addAttribute("listTenants", listTenants);
        model.addAttribute("serve", serve);
        model.addAttribute("pageTitle", "Create New Serve");
        return "serve/serve_form";
    }


    @PostMapping("/save")
    public String saveUser(Serve serve, RedirectAttributes redirectAttributes) throws IOException {
        service.save(serve);
        redirectAttributes.addFlashAttribute("message", "The service has been saved successfully");
        return getRedirectURLtoAffectedUser(serve);
    }


    private String getRedirectURLtoAffectedUser(Serve serve) {
        String firstName = serve.getName().split("@")[0];
        return "redirect:/serves/page/1?sortField=id&sortDir=asc&keyword=" + firstName;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Serve serve = service.getServeById(id);
            model.addAttribute("serve", serve);
            model.addAttribute("pageTitle", "Edit Serve  (ID: " + id + ")");
            return "organization/organization_form";
        } catch (ServeNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/serves";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "The serve ID "
                    + id + " has been deleted successfully");
        } catch (ServeNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/serves";
    }
}
