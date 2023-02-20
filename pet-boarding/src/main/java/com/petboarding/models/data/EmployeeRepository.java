package com.petboarding.models.data;

import com.petboarding.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    public Collection<Employee> findByPositionName(String positionName);
}
