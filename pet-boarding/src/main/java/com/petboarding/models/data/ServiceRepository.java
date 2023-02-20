package com.petboarding.models.data;

import com.petboarding.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    public Collection<Service> findByStayService(Boolean isStayService);
}
