package com.talan.polaris.repositories;

import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.CareerStepEntity;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link CareerStepEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Repository
public interface CareerStepRepository extends GenericRepository<CareerStepEntity> {

}
