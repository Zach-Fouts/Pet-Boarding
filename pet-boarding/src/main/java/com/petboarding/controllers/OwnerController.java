package com.petboarding.controllers;

import com.petboarding.models.Owner;
import com.petboarding.models.data.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("owners")
public class OwnerController extends AppBaseController{

    @Autowired
    private OwnerRepository ownerRepository;

// Owner Home Page

    @RequestMapping()
    public String showMainOwnerPage(Model model) {
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
    @GetMapping("/add")
    public String displayAddOwnerForm(Model model){
        this.setActiveModule("owners", model);
        model.addAttribute(new Owner());
        return "owners/add";
    }

    @PostMapping("add")
    public String processAddOwnerForm(@Valid @ModelAttribute Owner newOwner, Errors errors, Model model){
        this.setActiveModule("owners", model);
        if(errors.hasErrors()){
            return "owners/add";
        }
        model.addAttribute("owners", ownerRepository.save(newOwner));
        return "redirect:../owners";
    }

// View an individual Owner
    @GetMapping("view/{ownerId}")
    public String displayViewOwner(Model model, @PathVariable int ownerId){
        this.setActiveModule("owners", model);
        Optional optOwner = ownerRepository.findById(ownerId);
        if(optOwner.isPresent()){
            Owner owner = (Owner) optOwner.get();
            model.addAttribute("owner", owner);
            return "owners/view";
        } else {
            return "redirect:../owners";
        }
    }

// Updating Owners
    @GetMapping("updateOwner/{ownerId}")
    public String displayUpdateOwner(Model model, @PathVariable int ownerId){
        this.setActiveModule("owners", model);
        Optional optOwner = ownerRepository.findById(ownerId);
        if(optOwner.isPresent()){
            Owner owner = (Owner) optOwner.get();
            model.addAttribute("owner", owner);
            return "owners/updateOwner";
        } else {
            return "redirect:../owners";
        }
    }

    @PostMapping("updateOwner/{ownerId}")
    public String updateOwner(@Valid @ModelAttribute Owner owner, Errors errors, Model model, @PathVariable int ownerId){
        this.setActiveModule("owners", model);
        if(errors.hasErrors()){
            return "owners/updateOwner";
        }
        Owner updatedOwner = ownerRepository.findById(ownerId).get();
        updatedOwner.setFirstName(owner.getFirstName());
        updatedOwner.setLastName(owner.getLastName());
        updatedOwner.setAddress(owner.getAddress());
        updatedOwner.setAddress2(owner.getAddress2());
        updatedOwner.setPhoneNumber(owner.getPhoneNumber());
        updatedOwner.setEmail(owner.getEmail());
        updatedOwner.setNotes(owner.getNotes());
        ownerRepository.save(updatedOwner);

        return "redirect:/owners";
    }



// Delete Owner
    @RequestMapping("/{ownerId}")
    public String deleteOwner(@PathVariable("ownerId") int ownerId){
        ownerRepository.deleteById(ownerId);

        return "redirect:../owners";
    }


}
