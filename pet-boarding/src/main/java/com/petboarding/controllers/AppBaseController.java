package com.petboarding.controllers;

import com.petboarding.models.app.Module;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

public abstract class AppBaseController {
    // TODO: Move the modules to a table in the database
    List<Module> appModules = new ArrayList<>();

    public AppBaseController() {
        appModules.add(new Module("home", "Home", "bi-house", "employee"));
        appModules.add(new Module("owners", "Pet Owners", "bi-person-vcard", "employee"));
        appModules.add(new Module("pets", "Pets", "bi-circle", "employee"));
        appModules.add(new Module("reservations", "Reservations", "bi-calendar-week", "employee"));
        appModules.add(new Module("kennels", "Kennels", "bi-grid-3x3-gap-fill", "employee"));
        appModules.add(new Module("employees", "Employees", "bi-person-rolodex", "admin"));
        appModules.add(new Module("users", "Users", "bi-people-fill", "admin"));
    }

    @ModelAttribute("modules")
    public List<Module> addAppModules() {
        return appModules;
    }

    public void setActiveModule(String activeModule, Model model) {
        model.addAttribute("activeModule",
                appModules
                        .stream()
                        .filter(module -> activeModule.equalsIgnoreCase(module.getPath()))
                        .findFirst()
                        .orElse(Module.dummyFactory())
        );
    }
}