package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Client;
import com.kubstu.programm.entity.Employee;
import com.kubstu.programm.entity.Order;
import com.kubstu.programm.entity.Tour;
import com.kubstu.programm.exception.OrderNotFoundException;
import com.kubstu.programm.service.ClientService;
import com.kubstu.programm.service.EmployeeService;
import com.kubstu.programm.service.OrderService;
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

/**
 * DECRIPTION
 *
 * @author Mark Sinakaev
 * @version 1.0
 */

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TourService tourService;

    @Autowired
    private ClientService clientService;

    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Order> page = orderService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Order> listOrder = page.getContent();
        long startCount = (pageNum - 1) * OrderService.ORDER_PER_PAGE + 1;
        long endCount = startCount + OrderService.ORDER_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "order/order";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Order order = new Order();
        List<Employee> employeeList = employeeService.getEmployees();
        List<Tour> tourList = tourService.getTours();
        List<Client> clientList = clientService.getClients();

        model.addAttribute("employeeList", employeeList);
        model.addAttribute("tourList", tourList);
        model.addAttribute("clientList", clientList);

        model.addAttribute("order", order);
        model.addAttribute("pageTitle", "Create New Order");

        return "order/order_form";
    }


    @PostMapping("/save")
    public String saveUser(Order order, RedirectAttributes redirectAttributes) throws IOException {
        orderService.save(order);
        redirectAttributes.addFlashAttribute("message", "The order has been saved successfully");
        return getRedirectURLtoAffectedUser(order);
    }


    private String getRedirectURLtoAffectedUser(Order order) {
        Integer id = order.getId();
        return "redirect:/orders/page/1?sortField=id&sortDir=asc&keyword=" + id;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Order order = orderService.getOrderById(id);
            List<Employee> employeeList = employeeService.getEmployees();
            List<Tour> tourList = tourService.getTours();
            List<Client> clientList = clientService.getClients();

            model.addAttribute("employeeList", employeeList);
            model.addAttribute("tourList", tourList);
            model.addAttribute("clientList", clientList);
            model.addAttribute("order", order);
            model.addAttribute("pageTitle", "Edit order  (ID: " + id + ")");
            return "order/order_form";
        } catch (OrderNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/orders";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            orderService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The order ID "
                    + id + " has been deleted successfully");
        } catch (OrderNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/orders";
    }
}
