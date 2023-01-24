package com.petboarding.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController extends AppBaseController {

    @GetMapping("/home")
    public String showDashboardHome(Model model) {
        this.setActiveModule("home", model);
        return "index";
    }

    @GetMapping("/test")
    public String showProducts(Model model) {
        this.setActiveModule("owners", model);
        return "content-example-page";
    }

}
