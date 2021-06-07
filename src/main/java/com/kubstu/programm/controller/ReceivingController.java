package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Operation;
import com.kubstu.programm.entity.Receiving;
import com.kubstu.programm.exception.DippatchNotFoundException;
import com.kubstu.programm.exception.ReceivingNotFoundException;
import com.kubstu.programm.service.OperationService;
import com.kubstu.programm.service.ReceivingService;
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
@RequestMapping("/receivings")
public class ReceivingController {
    @Autowired
    private ReceivingService receivingService;

    @Autowired
    private OperationService operationService;


    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Receiving> page = receivingService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Receiving> listReceiving = page.getContent();
        long startCount = (pageNum - 1) * ReceivingService.RECEIVING_PER_PAGE + 1;
        long endCount = startCount + ReceivingService.RECEIVING_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listReceiving", listReceiving);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "receiving/receiving";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Receiving receiving = new Receiving();
        List<Operation> operationList = operationService.getOperations();

        model.addAttribute("operationList", operationList);
        model.addAttribute("receiving", receiving);
        model.addAttribute("pageTitle", "Create New receiving");

        return "receiving/receiving_form";
    }


    @PostMapping("/save")
    public String saveUser(Receiving receiving, RedirectAttributes redirectAttributes) throws IOException {
        receivingService.save(receiving);
        redirectAttributes.addFlashAttribute("message", "The receiving has been saved successfully");
        return getRedirectURLtoAffectedUser(receiving);
    }


    private String getRedirectURLtoAffectedUser(Receiving receiving) {
        Integer id = receiving.getId();
        return "redirect:/receivings/page/1?sortField=id&sortDir=asc&keyword=" + id;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Receiving receiving = receivingService.getReceivingById(id);
            List<Operation> operationList = operationService.getOperations();
            model.addAttribute("operationList", operationList);
            model.addAttribute("receiving", receiving);
            model.addAttribute("pageTitle", "Edit receiving  (ID: " + id + ")");
            return "receiving/receiving_form";
        } catch (ReceivingNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/receivings";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            receivingService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The receivings ID "
                    + id + " has been deleted successfully");
        } catch (ReceivingNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/receivings";
    }
}