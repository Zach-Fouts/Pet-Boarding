package com.petboarding.controllers;

import com.petboarding.models.Employee;
import com.petboarding.models.data.EmployeeRepository;
import com.petboarding.models.data.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("employees")
public class EmployeesController extends AppBaseController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;


    @GetMapping
    public String displayEmployeesGrid(Model model) {
        setActiveModule("employees", model);
        model.addAttribute("employees", employeeRepository.findAll());
        return "employees/index";
    }

    @GetMapping("add")
    public String displayAddEmployeeForm(Model model) {
        setActiveModule("employees", model);
        model.addAttribute("employee", new Employee());
        model.addAttribute("positions", positionRepository.findAll());
        model.addAttribute("formTitle", "New Employee");
        return "employees/form";
    }
}
