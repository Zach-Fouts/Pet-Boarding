package com.petboarding.models.data;

import com.petboarding.models.PetService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PetServiceRepository extends JpaRepository<PetService, Integer> {
    public Collection<PetService> findByStayService(Boolean isStayService);
}
