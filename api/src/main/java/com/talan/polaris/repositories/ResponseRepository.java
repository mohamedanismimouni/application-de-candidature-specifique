package com.talan.polaris.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.ResponseEntity;
import com.talan.polaris.enumerations.SurveyTypeEnum;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link ResponseEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Repository
public interface ResponseRepository extends GenericRepository<ResponseEntity> {

    @Query("SELECT response FROM ResponseEntity AS response "
            + "WHERE response.question.id LIKE :questionId "
            + "AND response.collaborator.id = :collaboratorId")
    public ResponseEntity findResponseByQuestionIdAndCollaboratorId(
            @Param("questionId") String questionId,
            @Param("collaboratorId") Long collaboratorId);

    @Query("SELECT response FROM ResponseEntity AS response "
            + "WHERE response.collaborator.id = :collaboratorId "
            + "AND response.question.surveyType LIKE :surveyType "
            + "AND (:evaluationId IS NULL OR response.evaluation.id LIKE :evaluationId) "
            + "ORDER BY response.question.position ASC")
    public Collection<ResponseEntity> findResponsesByCollaboratorIdAndSurveyType(
            @Param("collaboratorId") Long collaboratorId,
            @Param("surveyType") SurveyTypeEnum surveyType,
            @Param("evaluationId")String evaluationId);

}
