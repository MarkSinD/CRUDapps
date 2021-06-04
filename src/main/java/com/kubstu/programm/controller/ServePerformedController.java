package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Serve;
import com.kubstu.programm.entity.ServicePerformed;
import com.kubstu.programm.exception.ServePerformedNotFoundException;
import com.kubstu.programm.service.ServeService;
import com.kubstu.programm.service.ServicePerformedService;
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
@RequestMapping("/serveperformed")
public class ServePerformedController {

    @Autowired
    private ServicePerformedService servicePerformedService;

    @Autowired
    private ServeService serveService;

    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<ServicePerformed> page = servicePerformedService.listByPage(pageNum, sortField, sortDir, keyword);
        List<ServicePerformed> listServicePerformed = page.getContent();
        long startCount = (pageNum - 1) * ServicePerformedService.SERVICE_PERFORMED_PER_PAGE + 1;
        long endCount = startCount + ServicePerformedService.SERVICE_PERFORMED_PER_PAGE - 1;

        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listServicePerformed", listServicePerformed);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "serve_performed/serve_performed";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        ServicePerformed servicePerformed = new ServicePerformed();
        List<Serve> serveList = serveService.getServes();

        model.addAttribute("serveList", serveList);
        model.addAttribute("servicePerformed", servicePerformed);
        model.addAttribute("pageTitle", "Create New Service Performed");

        return "serve_performed/serve_performed_form";
    }


    @PostMapping("/save")
    public String saveUser(ServicePerformed servicePerformed, RedirectAttributes redirectAttributes) throws IOException {
        servicePerformedService.save(servicePerformed);
        redirectAttributes.addFlashAttribute("message", "The service performed has been saved successfully");
        return getRedirectURLtoAffectedUser(servicePerformed);
    }


    private String getRedirectURLtoAffectedUser(ServicePerformed servicePerformed) {
        Integer servicePerformedId = servicePerformed.getId();
        return "redirect:/serveperformed/page/1?sortField=id&sortDir=asc&keyword=" + servicePerformedId;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            ServicePerformed servicePerformed = servicePerformedService.getServicePerformedById(id);
            model.addAttribute("servicePerformed", servicePerformed);
            model.addAttribute("pageTitle", "Edit Serve Performed  (ID: " + id + ")");
            return "serve_performed/serve_performed_form";
        } catch (ServePerformedNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/serveperformed";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            servicePerformedService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The serve performed ID "
                    + id + " has been deleted successfully");
        } catch (ServePerformedNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/serveperformed";
    }
}

