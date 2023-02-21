package com.petboarding.models.data;

import com.petboarding.models.Kennel;
import org.springframework.stereotype.Repository;

@Repository
public interface KennelRepository extends JPARepositoryActiveFiltering<Kennel, Integer>{
}
