package com.petboarding.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/home")
    public String showDashboardHome(Model model) {
        return "home";
    }

    @GetMapping("/products")
    public String showProducts(Model model) {
        return "products";
    }

}
