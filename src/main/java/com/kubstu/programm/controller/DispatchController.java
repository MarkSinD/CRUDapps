
package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Operation;
import com.kubstu.programm.service.DispatchService;
import com.kubstu.programm.entity.Dispatch;
import com.kubstu.programm.exception.DippatchNotFoundException;
import com.kubstu.programm.service.OperationService;
import jdk.nashorn.internal.runtime.logging.Logger;
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
@RequestMapping("/dispatches")
public class DispatchController {

    @Autowired
    private DispatchService dispatchService;

    @Autowired
    private OperationService operationService;


    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Dispatch> page = dispatchService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Dispatch> listDispatch = page.getContent();
        long startCount = (pageNum - 1) * DispatchService.DISPATCH_PER_PAGE + 1;
        long endCount = startCount + DispatchService.DISPATCH_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listDispatch", listDispatch);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "dispatch/dispatch";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Dispatch dispatch = new Dispatch();
        List<Operation> operationList = operationService.getOperations();

        model.addAttribute("operationList", operationList);
        model.addAttribute("dispatch", dispatch);
        model.addAttribute("pageTitle", "Create New Dispatch");

        return "dispatch/dispatch_form";
    }


    @PostMapping("/save")
    public String saveUser(Dispatch dispatch, RedirectAttributes redirectAttributes) throws IOException {
        dispatchService.save(dispatch);
        redirectAttributes.addFlashAttribute("message", "The dispatch has been saved successfully");
        return getRedirectURLtoAffectedUser(dispatch);
    }


    private String getRedirectURLtoAffectedUser(Dispatch dispatch) {
        Integer id = dispatch.getId();
        return "redirect:/dispatches/page/1?sortField=id&sortDir=asc&keyword=" + id;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Dispatch dispatch = dispatchService.getAdministrationById(id);
            model.addAttribute("dispatch", dispatch);
            model.addAttribute("pageTitle", "Edit dispatch  (ID: " + id + ")");
            return "dispatch/dispatch_form";
        } catch (DippatchNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/dispatches";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            dispatchService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The dispatch ID "
                    + id + " has been deleted successfully");
        } catch (DippatchNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/dispatches";
    }
}

