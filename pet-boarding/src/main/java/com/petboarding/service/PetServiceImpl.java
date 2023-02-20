package com.petboarding.service;

import com.petboarding.models.Pet;
import com.petboarding.models.data.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService{
    // what do created methods do
    @Autowired
    private PetRepository petRepository;
    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public void savePet(Pet pet) {
        this.petRepository.save(pet);
    }

    @Override
    public Pet getPetById(Integer id) {
        Optional<Pet> optional = petRepository.findById(id);
        Pet pet = null;
        if(optional.isPresent()) {
            pet =optional.get();
        }else{
            throw new RuntimeException("Pet not found with id :: " + id);
        }
        return pet;
    }

    @Override
    public void deletePetById(Integer id) {
        this.petRepository.deleteById(id);
    }

    @Override
    public Page<Pet> findPage(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo -1, pageSize, sort);
        return this.petRepository.findAll(pageable);
    }
}
