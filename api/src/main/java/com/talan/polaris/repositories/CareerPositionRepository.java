package com.talan.polaris.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.CareerPositionEntity;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link CareerPositionEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Repository
public interface CareerPositionRepository  extends JpaRepository<CareerPositionEntity, Long> {
    
    @Query("SELECT careerPosition FROM CareerPositionEntity AS careerPosition "
            + "WHERE careerPosition.collaborator.id = :collaboratorId "
            + "AND (:status IS NULL OR careerPosition.status LIKE :status) "
            + "ORDER BY careerPosition.startedAt ASC")
    public Collection<CareerPositionEntity> findCareerPositionsByCollaboratorIdAndStatus(
            @Param("collaboratorId") Long collaboratorId,
            @Param("status") CareerPositionStatusEnum status);

}
