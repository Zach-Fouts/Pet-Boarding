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
@RequestMapping("myprofile")
public class EmployeeProfileController extends AppBaseController{

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("")
    public String displayEmployeeProfile(Model model, RedirectAttributes redirectAttributes, HttpSession httpSession){

        User user = (User) httpSession.getAttribute("user");

        model.addAttribute("employee", user.getEmployee());
        addLocation("my profile/" + user.getEmployee().getFullName(), model);
        return "employees/profile";
    }

    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule("employees");
    }

}
