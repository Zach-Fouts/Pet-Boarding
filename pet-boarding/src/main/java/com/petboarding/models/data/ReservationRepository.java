package com.petboarding.models.data;


import com.petboarding.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ReservationRepository extends JPARepositoryActiveFiltering<Reservation, Integer> {
    public List<Reservation> findByPetId(Integer petId);
    public List<Reservation> findByStayIsNull();

}
