package com.petboarding.controllers;

import com.petboarding.models.Employee;
import com.petboarding.models.User;
import com.petboarding.models.app.Module;
import com.petboarding.models.data.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("profile/{id}")
public class EmployeeProfileController extends AppBaseController{

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("")
    public String displayEmployeeProfile(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes, HttpSession httpSession){
        Optional<Employee> optEmployee = employeeRepository.findById(id);
        if(optEmployee.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "The employee ID:" + id + " couldn't be found.");
            return "redirect:/";
        }
        User user = (User) httpSession.getAttribute("user");

        if(user.getEmployee().getId() == id) {
        model.addAttribute("employee", optEmployee.get());
        this.setActiveModule(user.getEmployee().getFullName(), model);
        return "employees/profile";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "The employee ID:" + id + " couldn't be found.");
        return "redirect:/";

    }

}
