package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Serve;
import com.kubstu.programm.entity.Tour;
import com.kubstu.programm.exception.TourNotFoundException;
import com.kubstu.programm.service.ServeService;
import com.kubstu.programm.service.TourService;
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
@RequestMapping("/tours")
public class TourController {

    @Autowired
    private TourService tourService;

    @Autowired
    private ServeService serveService;


    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Tour> page = tourService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Tour> listAdministration = page.getContent();
        long startCount = (pageNum - 1) * TourService.TOUR_PER_PAGE + 1;
        long endCount = startCount + TourService.TOUR_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listAdministration", listAdministration);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "tour/tour";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Tour tour = new Tour();
        List<Serve> serveList = serveService.getServes();

        model.addAttribute("serveList", serveList);
        model.addAttribute("tour", tour);
        model.addAttribute("pageTitle", "Create New Tour");

        return "tour/tour_form";
    }


    @PostMapping("/save")
    public String saveUser(Tour tour, RedirectAttributes redirectAttributes) throws IOException {
        tourService.save(tour);
        redirectAttributes.addFlashAttribute("message", "The tour has been saved successfully");
        return getRedirectURLtoAffectedUser(tour);
    }


    private String getRedirectURLtoAffectedUser(Tour tour) {
        Integer id = tour.getId();
        return "redirect:/tours/page/1?sortField=id&sortDir=asc&keyword=" + id;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Tour tour = tourService.getTourById(id);
            List<Serve> serveList = serveService.getServes();
            model.addAttribute("serveList", serveList);
            model.addAttribute("tour", tour);
            model.addAttribute("pageTitle", "Edit tour  (ID: " + id + ")");
            return "tour/tour_form";
        } catch (TourNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/tours";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            tourService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The tour ID "
                    + id + " has been deleted successfully");
        } catch (TourNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/tours";
    }
}
