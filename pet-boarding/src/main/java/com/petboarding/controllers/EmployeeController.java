package com.petboarding.controllers;

import com.petboarding.models.data.EmployeeRepository;
import com.petboarding.models.data.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    EmployeeRepository employeeRepository;


}
