package com.petboarding.controllers;

import com.petboarding.models.Kennel;
import com.petboarding.models.Pet;
import com.petboarding.models.data.KennelRepository;
import com.petboarding.models.data.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("kennels")
public class KennelController extends AppBaseController{

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private KennelRepository kennelRepository;


    @RequestMapping("")
    public String showKennelMap(Model model){
        Iterable<Pet> pets;
        pets = petRepository.findAll();

        this.setActiveModule("kennels", model);
        model.addAttribute("pets", pets);
        return "kennels/kennelMap";
    }
    @RequestMapping("/grid")
    public String showKennelGrid(Model model){
        Iterable<Kennel> kennels;
        kennels = kennelRepository.findAll();

        this.setActiveModule("kennels", model);
        model.addAttribute("kennels", kennels);
        return "kennels/kennelGrid";
    }
}
