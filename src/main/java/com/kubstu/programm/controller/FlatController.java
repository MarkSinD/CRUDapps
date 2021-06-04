package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Flat;
import com.kubstu.programm.entity.House;
import com.kubstu.programm.entity.Organization;
import com.kubstu.programm.exception.FlatNotFoundException;
import com.kubstu.programm.exception.OrganizationNotFoundException;
import com.kubstu.programm.service.FlatService;
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
@RequestMapping("/flats")
public class FlatController {

    @Autowired
    private FlatService service;

    @Autowired
    private HouseService houseService;

    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Flat> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<Flat> listFlat = page.getContent();
        long startCount = (pageNum - 1) * FlatService.FLAT_PER_PAGE + 1;
        long endCount = startCount + FlatService.FLAT_PER_PAGE - 1;

        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listFlat", listFlat);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "flat/flat";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Flat flat = new Flat();
        List<House> listHouse = houseService.getHouses();
        model.addAttribute("listHouse", listHouse);
        model.addAttribute("flat", flat);
        model.addAttribute("pageTitle", "Create New Flat");

        return "flat/flat_form";
    }


    @PostMapping("/save")
    public String saveUser(Flat flat, RedirectAttributes redirectAttributes) throws IOException {
        service.save(flat);
        redirectAttributes.addFlashAttribute("message", "The flat has been saved successfully");
        return getRedirectURLtoAffectedUser(flat);
    }


    private String getRedirectURLtoAffectedUser(Flat flat) {
        Integer flatId = flat.getId();
        return "redirect:/flats/page/1?sortField=id&sortDir=asc&keyword=" + flatId;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Flat flat = service.getFlatById(id);
            model.addAttribute("flat", flat);
            model.addAttribute("pageTitle", "Edit Flat  (ID: " + id + ")");
            return "organization/organization_form";
        } catch (FlatNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/flats";
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
        } catch (FlatNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/flats";
    }
}

