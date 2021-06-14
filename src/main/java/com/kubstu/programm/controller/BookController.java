package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Book;
import com.kubstu.programm.entity.Client;
import com.kubstu.programm.entity.Good;
import com.kubstu.programm.entity.Serve;
import com.kubstu.programm.exception.BookNotFoundException;
import com.kubstu.programm.exception.ClientNotFoundException;
import com.kubstu.programm.service.BookService;
import com.kubstu.programm.service.ClientService;
import com.kubstu.programm.service.GoodService;
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


@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ServeService serveService;

    @Autowired
    private GoodService goodService;

    @Autowired
    private ClientService clientService;


    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Book> page = bookService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Book> listBook = page.getContent();
        long startCount = (pageNum - 1) * BookService.BOOK_PER_PAGE + 1;
        long endCount = startCount + BookService.BOOK_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listBook", listBook);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "book/book";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        Book book = new Book();
        List<Serve> serveList = serveService.getServes();
        List<Good> goodList = goodService.getGoods();
        List<Client> clientList = clientService.getClients();

        model.addAttribute("clientList", clientList);
        model.addAttribute("serveList", serveList);
        model.addAttribute("goodList", goodList);
        model.addAttribute("book", book);
        model.addAttribute("pageTitle", "Create New book");

        return "book/book_form";
    }


    @PostMapping("/save")
    public String saveBook(Book book, RedirectAttributes redirectAttributes) throws IOException {
        bookService.save(book);
        redirectAttributes.addFlashAttribute("message", "The book has been saved successfully");
        return getRedirectURLtoAffectedUser(book);
    }


    private String getRedirectURLtoAffectedUser(Book book) {
        Integer id = book.getId();
        return "redirect:/books/page/1?sortField=id&sortDir=asc&keyword=" + id;
    }


    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Book book = bookService.getBookById(id);
            List<Serve> serveList = serveService.getServes();
            List<Good> goodList = goodService.getGoods();
            List<Client> clientList = clientService.getClients();

            model.addAttribute("clientList", clientList);
            model.addAttribute("serveList", serveList);
            model.addAttribute("goodList", goodList);
            model.addAttribute("book", book);
            model.addAttribute("pageTitle", "Edit book  (ID: " + id + ")");
            return "book/book_form";
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/books";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            bookService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The book ID "
                    + id + " has been deleted successfully");
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/books";
    }
}
