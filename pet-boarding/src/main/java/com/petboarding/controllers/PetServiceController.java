package com.petboarding.controllers;

import com.petboarding.models.PetService;
import com.petboarding.models.app.Module;
import com.petboarding.models.data.PetServiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("petServices")
public class PetServiceController extends AppBaseController{

    @Autowired
    private PetServiceRepository petServiceRepository;

    @GetMapping("")
    public String listServices(Model model) {
        List<PetService> services = petServiceRepository.findAll();
        model.addAttribute("listServices", services);
        addLocation("services", model);
        return "petServices/index";
    }

    @GetMapping("add")
    public String createServiceForm (Model model) {
        model.addAttribute("petService", new PetService());
        addLocation("services/add", model);
        return "petServices/add";
    }

    @PostMapping("add")
    public String saveService(@ModelAttribute @Valid PetService petService, BindingResult result, Errors errors) {
        if (errors.hasErrors()) {
            return "petServices/add";
        }
        petServiceRepository.save(petService);
        return "redirect:/petServices";
    }

    @GetMapping("update/{id}")
    public String updateServiceForm(@PathVariable Integer id, Model model) {
        model.addAttribute("petService", petServiceRepository.findById(id));
        return "petServices/update";
    }

    @Transactional
    @PostMapping("update")
    public String updateService(@RequestParam Integer id, @RequestParam String name, @RequestParam double pricePerUnit, @ModelAttribute @Valid PetService petService, BindingResult result, Errors errors) {
        if (errors.hasErrors()) {
            return "petServices/update";
        }
        Optional<PetService> existingService = petServiceRepository.findById(id);
        if (existingService.isPresent()) {
            existingService.get().setName(name);
            existingService.get().setPricePerUnit(pricePerUnit);
        }
        return "redirect:/petServices";
    }

    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule("reservations");
    }
}