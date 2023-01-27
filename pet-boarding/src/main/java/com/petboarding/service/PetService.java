package com.petboarding.service;

import com.petboarding.models.Pet;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PetService {
    // create methods for Pets
    List<Pet> getAllPets();
    void savePet(Pet pet);

    Pet getPetById(Long id);
    void deletePetById(long id);
    Page<Pet> findPage(int pageNo, int pageSize, String sortField, String sortDirection);
}