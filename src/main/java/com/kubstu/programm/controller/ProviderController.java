package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Provider;
import com.kubstu.programm.entity.Serve;
import com.kubstu.programm.exception.ProviderNotFoundException;
import com.kubstu.programm.service.ProviderService;
import com.kubstu.programm.service.ServeService;
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

/**
 * DECRIPTION
 *
 * @author Mark Sinakaev
 * @version 1.0
 */

@Controller
@RequestMapping("/providers")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @Autowired
    private ServeService serveService;


    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Provider> page = providerService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Provider> listProvider  = page.getContent();
        long startCount = (pageNum - 1) * ProviderService.PROVIDER_PER_PAGE + 1;
        long endCount = startCount + ProviderService.PROVIDER_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listProvider", listProvider);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "provider/provider";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Provider provider = new Provider();
        List<Serve> serveList = serveService.getServes();

        model.addAttribute("serveList", serveList);
        model.addAttribute("provider", provider);
        model.addAttribute("pageTitle", "Create New Provider");

        return "provider/provider_form";
    }


    @PostMapping("/save")
    public String saveUser(Provider provider, RedirectAttributes redirectAttributes) throws IOException {
        providerService.save(provider);
        redirectAttributes.addFlashAttribute("message", "The provider has been saved successfully");
        return getRedirectURLtoAffectedUser(provider);
    }


    private String getRedirectURLtoAffectedUser(Provider provider) {
        String firstName = provider.getName().split("@")[0];
        return "redirect:/providers/page/1?sortField=id&sortDir=asc&keyword=" + firstName;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Provider provider = providerService.getProviderById(id);

            model.addAttribute("provider", provider);
            model.addAttribute("pageTitle", "Edit provider  (ID: " + id + ")");
            return "provider/provider_form";
        } catch (ProviderNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/providers";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            providerService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The provider ID "
                    + id + " has been deleted successfully");
        } catch (ProviderNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/providers";
    }
}
