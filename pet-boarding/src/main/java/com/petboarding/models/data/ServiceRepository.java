package com.petboarding.models.data;

import com.petboarding.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
    public Collection<Service> findByStayService(Boolean isStayService);
}
