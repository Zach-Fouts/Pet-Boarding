package com.petboarding.models.data;


import com.petboarding.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReservationRepository extends JPARepositoryActiveFiltering<Reservation, Integer> {
    public Collection<Reservation> findByPetId(Integer petId);
}
