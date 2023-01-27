package com.petboarding.controllers;

import com.petboarding.models.app.Module;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController extends AppBaseController {

    @GetMapping
    public String showIndex() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String showDashboardHome(Model model) {
        return "index";
    }

    @GetMapping("/test")
    public String showProducts(Model model) {
        this.setActiveModule("owners", model);
        return "content-example-page";
    }

    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule("home");
    }

}
