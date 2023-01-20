package com.petboarding.controllers;

import com.petboarding.models.Owner;
import com.petboarding.models.data.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("owners")
public class OwnerController {

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping(value = "add")
    public String displayAddOwnerForm(Model model){
        model.addAttribute(new Owner());
        return "owners/add";
    }

    @PostMapping("add")
    public String processAddOwnerForm(@ModelAttribute Owner newOwner, Error errors, Model model){

        model.addAttribute("owners", ownerRepository.save(newOwner));

        return "redirect:";
    }

    @GetMapping("view/{ownerId}")
    public String displayViewEmployer(Model model, @PathVariable int ownerId){
        Optional optOwner = ownerRepository.findById(ownerId);
        if(optOwner.isPresent()){
            Owner owner = (Owner) optOwner.get();
            model.addAttribute("owner", owner);
            return "owners/view";
        } else {
            return "redirect:../";
        }
    }

    @RequestMapping("")
    public String index(Model model){
        Iterable<Owner> owners;
        owners = ownerRepository.findAll();
        model.addAttribute("owners", owners);
        return "owners/index";
    }
}
