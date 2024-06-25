package com.talan.polaris.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.EvaluationEntity;
import com.talan.polaris.enumerations.EvaluationStatusEnum;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link EvaluationEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Repository
public interface EvaluationRepository extends GenericRepository<EvaluationEntity> {

    @Query("SELECT evaluation FROM EvaluationEntity AS evaluation "
            + "WHERE evaluation.careerPosition.collaborator.id = :collaboratorId "
            + "AND (:supervisorId IS NULL OR evaluation.careerPosition.supervisor.id = :supervisorId) "
            + "AND (:evaluationStatus IS NULL OR evaluation.status LIKE :evaluationStatus)")
    public Collection<EvaluationEntity> findEvaluations(
            @Param("collaboratorId") Long collaboratorId,
            @Param("supervisorId") Long supervisorId,
            @Param("evaluationStatus") EvaluationStatusEnum evaluationStatus);

    @Query("SELECT evaluation FROM EvaluationEntity AS evaluation "
            + "WHERE evaluation.careerPosition.collaborator.memberOf.id LIKE :teamId "
            + "AND (:evaluationStatus IS NULL OR evaluation.status LIKE :evaluationStatus)")
    public Collection<EvaluationEntity> findTeamEvaluations(
            @Param("teamId") String teamId,
            @Param("evaluationStatus") EvaluationStatusEnum evaluationStatus);

}
