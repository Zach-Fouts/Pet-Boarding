package com.petboarding.models.data;

import com.petboarding.models.Stay;
import com.petboarding.models.StayStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StayRepository extends JpaRepository<Stay, Integer> {
}
