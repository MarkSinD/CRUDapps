package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Edition;
import com.kubstu.programm.entity.Subscription;
import com.kubstu.programm.exception.DippatchNotFoundException;
import com.kubstu.programm.exception.EditionNotFoundException;
import com.kubstu.programm.service.EditionService;
import com.kubstu.programm.service.SubsrciptionService;
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
@RequestMapping("/editions")
public class EditionController {

    @Autowired
    private EditionService editionService;

    @Autowired
    private SubsrciptionService subsrciptionService;

    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Edition> page = editionService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Edition> listEdition = page.getContent();

        long startCount = (pageNum - 1) * EditionService.EDITION_PER_PAGE + 1;
        long endCount = startCount + EditionService.EDITION_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listEdition", listEdition);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "edition/edition";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Edition edition = new Edition();

        List<Subscription> subscriptionList = subsrciptionService.getSubscriptions();

        model.addAttribute("subscriptionList", subscriptionList);
        model.addAttribute("edition", edition);
        model.addAttribute("pageTitle", "Create New Edition");

        return "edition/edition_form";
    }


    @PostMapping("/save")
    public String saveUser(Edition edition, RedirectAttributes redirectAttributes) throws IOException {
        editionService.save(edition);
        redirectAttributes.addFlashAttribute("message", "The edition has been saved successfully");
        return getRedirectURLtoAffectedUser(edition);
    }


    private String getRedirectURLtoAffectedUser(Edition edition) {
        String firstName = edition.getName().split("@")[0];
        return "redirect:/editions/page/1?sortField=id&sortDir=asc&keyword=" + firstName;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Edition edition = editionService.getEditionById(id);List<Subscription> subscriptionList = subsrciptionService.getSubscriptions();

            model.addAttribute("subscriptionList", subscriptionList);
            model.addAttribute("edition", edition);
            model.addAttribute("pageTitle", "Edit edition  (ID: " + id + ")");
            return "edition/edition_form";
        } catch (EditionNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/editions";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            editionService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The edition ID "
                    + id + " has been deleted successfully");
        } catch (EditionNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/editions";
    }
}