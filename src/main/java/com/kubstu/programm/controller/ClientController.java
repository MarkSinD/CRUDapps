package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Client;
import com.kubstu.programm.exception.ClientNotFoundException;
import com.kubstu.programm.service.ClientService;
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
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Client> page = clientService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Client> listClient = page.getContent();
        long startCount = (pageNum - 1) * ClientService.CLIENT_PER_PAGE + 1;
        long endCount = startCount + ClientService.CLIENT_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listClient", listClient);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "client/client";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Client client = new Client();

        model.addAttribute("client", client);
        model.addAttribute("pageTitle", "Create New Client");

        return "client/client_form";
    }


    @PostMapping("/save")
    public String saveUser(Client client, RedirectAttributes redirectAttributes) throws IOException {
        clientService.save(client);
        redirectAttributes.addFlashAttribute("message", "The client has been saved successfully");
        return getRedirectURLtoAffectedUser(client);
    }


    private String getRedirectURLtoAffectedUser(Client client) {
        String firstName = client.getName().split("@")[0];
        return "redirect:/clients/page/1?sortField=id&sortDir=asc&keyword=" + firstName;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Client client = clientService.getClientById(id);
            model.addAttribute("client", client);
            model.addAttribute("pageTitle", "Edit client  (ID: " + id + ")");
            return "client/client_form";
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/clients";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            clientService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The client ID "
                    + id + " has been deleted successfully");
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/clients";
    }
}
