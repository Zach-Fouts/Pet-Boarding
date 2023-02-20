package com.petboarding.controllers;

import com.petboarding.models.PetService;
import com.petboarding.models.data.PetServiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("petServices")
public class PetServiceController extends AppBaseController{

    @Autowired
    private PetServiceRepository petServiceRepository;

    @GetMapping("add")
    public String createServiceForm (Model model) {
        model.addAttribute("petService", new PetService());
        return "petServices/add";
    }

    @GetMapping("")
    public String listServices(Model model) {
        List<PetService> services = petServiceRepository.findAll();
        model.addAttribute("listServices", services);
        return "petServices/";
    }

    @GetMapping("update/{id}")
    public String updateServiceForm(@PathVariable Integer id, Model model) {
        model.addAttribute("petService", petServiceRepository.findById(id));
        return "petServices/update";
    }
}
