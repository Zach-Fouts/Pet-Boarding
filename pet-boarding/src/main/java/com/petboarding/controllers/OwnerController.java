package com.petboarding.controllers;

import com.petboarding.models.Owner;
import com.petboarding.models.data.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        return "redirect:../index";
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

    @GetMapping("updateOwner/{ownerId}")
    public String displayUpdateEmployer(Model model, @PathVariable int ownerId){
        Optional optOwner = ownerRepository.findById(ownerId);
        if(optOwner.isPresent()){
            Owner owner = (Owner) optOwner.get();
            model.addAttribute("owner", owner);
            return "owners/updateOwner";
        } else {
            return "redirect:../";
        }
    }

    @PostMapping("updateOwner/{ownerId}")
    public String updateOwner(@ModelAttribute Owner owner, Model model, @PathVariable int ownerId){
        Owner updatedOwner = ownerRepository.findById(ownerId).get();
        updatedOwner.setName(owner.getName());
        ownerRepository.save(updatedOwner);

        return "redirect:../index";
    }

    @RequestMapping("")
    public String index(Model model){
        Iterable<Owner> owners;
        owners = ownerRepository.findAll();
        model.addAttribute("owners", owners);
        return "owners/index";
    }

    @RequestMapping(value = "index")
    public String testPage(Model model) {
        Iterable<Owner> owners;
        owners = ownerRepository.findAll();
        model.addAttribute("title", "All Owners");
        model.addAttribute("owners", owners);

        return "owners/index";
    }

    @RequestMapping("/{ownerId}")
    public String deleteOwner(@PathVariable("ownerId") int ownerId){
        ownerRepository.deleteById(ownerId);

        return "redirect:../owners/index";
    }
}
