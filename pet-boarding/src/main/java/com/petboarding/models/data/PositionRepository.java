package com.petboarding.models.data;

import com.petboarding.models.Position;
import org.springframework.data.repository.CrudRepository;

public interface PositionRepository extends CrudRepository<Position, Integer> {
}
