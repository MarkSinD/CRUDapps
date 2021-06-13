package com.kubstu.programm.controller;

import com.kubstu.programm.entity.Employee;
import com.kubstu.programm.entity.Order;
import com.kubstu.programm.exception.EmployeeNotFoundException;
import com.kubstu.programm.service.EmployeeService;
import com.kubstu.programm.service.OrderService;
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
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OrderService orderService;


    @GetMapping("")
    public String listAll(Model model){
        return listByPage(1, model, "id", "asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum")int pageNum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword ){
        Page<Employee> page = employeeService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Employee> listEmployee = page.getContent();
        long startCount = (pageNum - 1) * EmployeeService.EMPLOYEE_PER_PAGE + 1;
        long endCount = startCount + EmployeeService.EMPLOYEE_PER_PAGE - 1;
        if(endCount > page.getTotalPages()){
            endCount = page.getTotalPages();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listEmployee", listEmployee);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "employee/employee";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Employee employee = new Employee();
        List<Order> orderList = orderService.getOrders();

        model.addAttribute("orderList", orderList);
        model.addAttribute("employee", employee);
        model.addAttribute("pageTitle", "Create New Employee");

        return "employee/employee_form";
    }


    @PostMapping("/save")
    public String saveUser(Employee employee, RedirectAttributes redirectAttributes) throws IOException {
        employeeService.save(employee);
        redirectAttributes.addFlashAttribute("message", "The employee has been saved successfully");
        return getRedirectURLtoAffectedUser(employee);
    }


    private String getRedirectURLtoAffectedUser(Employee employee) {
        String firstName = employee.getName().split("@")[0];
        return "redirect:/employees/page/1?sortField=id&sortDir=asc&keyword=" + firstName;
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){

        try {
            Employee employee = employeeService.getEmployeeById(id);
            List<Order> orderList = orderService.getOrders();

            model.addAttribute("orderList", orderList);
            model.addAttribute("employee", employee);
            model.addAttribute("pageTitle", "Edit employee  (ID: " + id + ")");
            return "employee/employee_form";
        } catch (EmployeeNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/employees";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            employeeService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The administration ID "
                    + id + " has been deleted successfully");
        } catch (EmployeeNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/employees";
    }
}
