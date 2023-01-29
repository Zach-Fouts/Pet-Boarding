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

    @GetMapping("/testError")
    public String showError(Model model) {
        model.addAttribute("errorMessage", "Something went wrong somewhere.");
        return "index";
    }

    @GetMapping("/testInfo")
    public String showInfo(Model model) {
        model.addAttribute("infoMessage", "Something has information for you or something.");
        return "index";
    }

    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule("home");
    }

}
