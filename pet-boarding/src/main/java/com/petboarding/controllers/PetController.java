package com.petboarding.controllers;

import com.petboarding.models.Owner;
import com.petboarding.models.Pet;
import com.petboarding.models.app.Module;
import com.petboarding.models.data.OwnerRepository;     // Needed to Grab Owners
import com.petboarding.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller

//@CrossOrigin(origins = "http://localhost:3000") for ReactJs
public class PetController extends AppBaseController {

    @Autowired
    private PetService petService;
    //private PetRepository petRepository;

    @Autowired   // Needed to grab owners
    private OwnerRepository ownerRepository;

    // display list of pets page
    @GetMapping("/pets")
    public String viewPetPage(Model model) {
        model.addAttribute("listPets", petService.getAllPets());
        return "pets/petPage";

    }

    @GetMapping("/showNewPetForm")
    public String showNewPetForm(Model model) {
        // create model attribute to bind form data
        Pet pet = new Pet();
        model.addAttribute("pet", pet);
        model.addAttribute("parents", ownerRepository.findAll());       // Grab all Owners
        return "pets/new_pet";
    }
    @GetMapping("/showNewPetForm/{ownerId}")
    public String showNewPetFormFromOwner(Model model, @PathVariable int ownerId) {
        // create model attribute to bind form data
        Pet pet = new Pet();
        model.addAttribute("pet", pet);
        Optional optOwner = ownerRepository.findById(ownerId);
        if(optOwner.isPresent()) {
            Owner owner = (Owner) optOwner.get();
            model.addAttribute("parents", owner);       // Grab specific Owner
            return "pets/new_pet";
        } else {
            return"redirect:../owners";
        }
    }

    @PostMapping("/savePet")
    public String savePet(@ModelAttribute("pet") @Valid Pet pet, Errors errors, Model model) {  // Added Model model for ownerRepository
        // save pet to database
        if (errors.hasErrors()) {
            model.addAttribute("parents", ownerRepository.findAll());   //Keeps Owner data on page
            return "pets/new_pet";
        }

        petService.savePet(pet);
        return "redirect:/pets";
    }

    @GetMapping("/showFormForUpdate/{id}")
        // show update form for id chosen
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Pet pet = petService.getPetById(id);
        // set pet model to form
        model.addAttribute("pet", pet);
        return "pets/update_pet";
    }
    @GetMapping("/deletePet/{id}")
    public String deletePet(@PathVariable (value = "id") long id) {
        // call delete method
        this.petService.deletePetById(id);
        return "redirect:/pets";
    }

    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule("pets");
    }
    // create pet api
//    @PostMapping("/pets")
//    public Pet createPet(@RequestBody Pet pet) {
//        return petRepository.save(pet);
//    }
//
//    // get all pets api
//    @GetMapping("/pets")
//    public List<Pet> getAllPets() {
//        return petRepository.findAll();
//    }
//
//    // get pet by id
//    @GetMapping("/pets/{id}")
//    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
//        Pet pet = petRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Pet with id : " + id + "does not exist"));
//        return ResponseEntity.ok(pet);
//    }
//
//    // update pet api
//    @PutMapping("/pets/{id}")
//    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody Pet petDetails) {
//        Pet pet = petRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Pet with id : " + id + "does not exist"));
//
//        pet.setPetName(petDetails.getPetName());
//        pet.setParents(pet.getParents());
//        pet.setBreed(pet.getBreed());
//        pet.setNotes(pet.getNotes());
//
//        Pet updatedPet = petRepository.save(pet);
//        return ResponseEntity.ok(updatedPet);
//    }

}
