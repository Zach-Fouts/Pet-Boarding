package com.petboarding.controllers;

import com.petboarding.models.Owner;
import com.petboarding.models.data.OwnerRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequestMapping("owners")
public class OwnerController extends AppBaseController{

    @Autowired
    private OwnerRepository ownerRepository;

// Owner Home Page
    @RequestMapping(value = "index")
    public String testPage(Model model) {
        Iterable<Owner> owners;
        owners = ownerRepository.findAll();
        model.addAttribute("title", "All Owners");
        model.addAttribute("owners", owners);
        this.setActiveModule("owners", model);

        return "owners/index";
    }

// Return to Pet Boarding Home page
    @GetMapping("/home")
    public String showDashboardHome(){
    return "redirect:../home";
}

// Adding Owners
    @GetMapping(value = "add")
    public String displayAddOwnerForm(Model model){
        this.setActiveModule("owners", model);
        model.addAttribute(new Owner());
        return "/owners/add";
    }

    @PostMapping("add")
    public String processAddOwnerForm(@ModelAttribute Owner newOwner, Error errors, @NotNull Model model){
        model.addAttribute("owners", ownerRepository.save(newOwner));
        return "redirect:../index";
    }

// View an individual Owner
    @GetMapping("view/{ownerId}")
    public String displayViewEmployer(Model model, @PathVariable int ownerId){
        this.setActiveModule("owners", model);
        Optional optOwner = ownerRepository.findById(ownerId);
        if(optOwner.isPresent()){
            Owner owner = (Owner) optOwner.get();
            model.addAttribute("owner", owner);
            return "owners/view";
        } else {
            return "redirect:../";
        }
    }

// Updating Owners
    @GetMapping("updateOwner/{ownerId}")
    public String displayUpdateEmployer(Model model, @PathVariable int ownerId){
        this.setActiveModule("owners", model);
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




    @RequestMapping("/{ownerId}")
    public String deleteOwner(@PathVariable("ownerId") int ownerId){
        ownerRepository.deleteById(ownerId);

        return "redirect:../owners/index";
    }


}
