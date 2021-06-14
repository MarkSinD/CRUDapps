package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Book;
import com.kubstu.programm.entity.Client;
import com.kubstu.programm.entity.Good;
import com.kubstu.programm.entity.Provider;
import com.kubstu.programm.exception.GoodNotFoundException;
import com.kubstu.programm.service.BookService;
import com.kubstu.programm.service.GoodService;
import com.kubstu.programm.service.ProviderService;
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
@RequestMapping("/goods")
public class GoodController {

    @Autowired
    private GoodService goodService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ProviderService providerService;


    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Good> page = goodService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Good> listGood = page.getContent();
        long startCount = (pageNum - 1) * GoodService.GOOD_PER_PAGE + 1;
        long endCount = startCount + GoodService.GOOD_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listGood", listGood);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "good/good";
    }

    @GetMapping("/new")
    public String newGood(Model model){
        Good good = new Good();
        List<Book> bookList = bookService.getBooks();
        List<Provider> providerList = providerService.getProviders();

        model.addAttribute("providerList", providerList);
        model.addAttribute("bookList", bookList);
        model.addAttribute("good", good);
        model.addAttribute("pageTitle", "Create New Good");

        return "good/good_form";
    }


    @PostMapping("/save")
    public String saveGood(Good good, RedirectAttributes redirectAttributes) throws IOException {
        goodService.save(good);
        redirectAttributes.addFlashAttribute("message", "The good has been saved successfully");
        return getRedirectURLtoAffectedUser(good);
    }


    private String getRedirectURLtoAffectedUser(Good good) {
        String firstName = good.getTitle();
        return "redirect:/goods/page/1?sortField=id&sortDir=asc&keyword=" + firstName;
    }


    @GetMapping("/edit/{id}")
    public String editGood(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Good good = goodService.getGoodById(id);
            model.addAttribute("good", good);
            model.addAttribute("pageTitle", "Edit good  (ID: " + id + ")");
            return "good/good_form";
        } catch (GoodNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/goods";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteGood(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            goodService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The good ID "
                    + id + " has been deleted successfully");
        } catch (GoodNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/goods";
    }
}
