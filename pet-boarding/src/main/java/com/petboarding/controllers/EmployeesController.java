package com.petboarding.controllers;

import com.petboarding.models.Employee;
import com.petboarding.models.app.Module;
import com.petboarding.models.data.EmployeeRepository;
import com.petboarding.models.data.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("employees")
public class EmployeesController extends AppBaseController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;


    @GetMapping
    public String displayEmployeesGrid(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employees/index";
    }

    @GetMapping("add")
    public String displayAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("positions", positionRepository.findAll());
        model.addAttribute("formTitle", "New Employee");
        return "employees/form";
    }

    @PostMapping("add")
    public String processAddEmployeeRequest(@Valid @ModelAttribute Employee newEmployee, Errors errors, Model model) {
        if(errors.hasErrors()) {
            return "employees/form";
        }

        employeeRepository.save(newEmployee);

        return "redirect:";
    }

    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule("employees");
    }
}
