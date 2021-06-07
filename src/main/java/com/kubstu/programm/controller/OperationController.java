package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Operation;
import com.kubstu.programm.exception.DippatchNotFoundException;
import com.kubstu.programm.exception.OperationNotFoundException;
import com.kubstu.programm.service.OperationService;
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
@RequestMapping("/operations")
public class OperationController {
    @Autowired
    private OperationService operationService;


    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Operation> page = operationService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Operation> listOperation = page.getContent();
        long startCount = (pageNum - 1) * OperationService.OPERATION_PER_PAGE + 1;
        long endCount = startCount + OperationService.OPERATION_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listOperation", listOperation);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "operation/operation";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Operation operation = new Operation();
        model.addAttribute("operation", operation);
        model.addAttribute("pageTitle", "Create New Administration");

        return "operation/operation_form";
    }


    @PostMapping("/save")
    public String saveUser(Operation operation, RedirectAttributes redirectAttributes) throws IOException {
        operationService.save(operation);
        redirectAttributes.addFlashAttribute("message", "The operation has been saved successfully");
        return getRedirectURLtoAffectedUser(operation);
    }


    private String getRedirectURLtoAffectedUser(Operation operation) {
        Integer id = operation.getId();
        return "redirect:/operations/page/1?sortField=id&sortDir=asc&keyword=" + id;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Operation operation = operationService.getOperationById(id);
            model.addAttribute("operation", operation);
            model.addAttribute("pageTitle", "Edit operation  (ID: " + id + ")");
            return "operation/operation_form";
        } catch (OperationNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/operations";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            operationService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The operation ID "
                    + id + " has been deleted successfully");
        } catch (OperationNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/operations";
    }
}