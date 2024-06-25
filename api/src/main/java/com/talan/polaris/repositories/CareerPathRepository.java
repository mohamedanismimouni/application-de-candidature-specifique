package com.talan.polaris.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.CareerPathEntity;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link CareerPathEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Repository
public interface CareerPathRepository extends GenericRepository<CareerPathEntity> {

    @Query("SELECT careerPath FROM CareerPathEntity AS careerPath "
            + "WHERE careerPath.toCareerStep.id LIKE :toCareerStepId")
    public Collection<CareerPathEntity> findInboundCareerPaths(
            @Param("toCareerStepId") String toCareerStepId);
    
    @Query("SELECT careerPath FROM CareerPathEntity AS careerPath "
            + "WHERE careerPath.fromCareerStep.id LIKE :fromCareerStepId")
    public Collection<CareerPathEntity> findOutboundCareerPaths(
            @Param("fromCareerStepId") String fromCareerStepId);

    @Modifying
    @Query("UPDATE CareerPathEntity AS careerPath "
            + "SET careerPath.toCareerStep.id = :newToCareerStepId "
            + "WHERE careerPath.toCareerStep.id LIKE :oldToCareerStepId "
            + "AND careerPath.fromCareerStep.id NOT LIKE :newToCareerStepId")
    public int moveInboundCareerPaths(
            @Param("newToCareerStepId") String newToCareerStepId,
            @Param("oldToCareerStepId") String oldToCareerStepId);

}
