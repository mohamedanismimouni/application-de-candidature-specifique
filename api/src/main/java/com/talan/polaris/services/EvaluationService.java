package com.talan.polaris.services;

import java.util.Collection;

import javax.json.JsonPatch;

import com.talan.polaris.entities.EvaluationEntity;
import com.talan.polaris.enumerations.EvaluationStatusEnum;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link EvaluationEntity}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface EvaluationService extends GenericService<EvaluationEntity> {

    /**
     * Finds evaluations having the given {@code collaboratorId} and with the 
     * given {@code supervisorId} and {@code status} if specified.
     * 
     * @param collaboratorId
     * @param supervisorId optional
     * @param evaluationStatus optional
     * 
     * @return the evaluations matching the given parameters if any.
     */
    public Collection<EvaluationEntity> findEvaluations(
            Long collaboratorId,
            Long supervisorId,
            EvaluationStatusEnum evaluationStatus);

    /**
     * Finds evaluations of team members given by {@code teamId} and with the given
     * {@code evaluationStatus} if specified.
     * 
     * @param teamId
     * @param evaluationStatus optional
     * 
     * @return the evaluations matching the given parameters if any.
     */
    public Collection<EvaluationEntity> findTeamEvaluations(
            String teamId,
            EvaluationStatusEnum evaluationStatus);

    /**
     * Creates the given {@code evaluation}.
     * <p>
     * Sets the {@code supervisorRating} and {@code supervisorFeedback} to
     * {@code null}, and sets the {@code status} to {@code OPEN}.
     * 
     * @param evaluation
     * 
     * @return the created evaluation.
     * 
     * @throws IllegalArgumentException if the specified career position does not 
     * have the status {@code CURRENT}, or if the evaluation date is in the past, 
     * or if the specified collaborator associated to that career position has 
     * already an {@code OPEN} evaluation.
     */
    public EvaluationEntity createEvaluation(EvaluationEntity evaluation);

    /**
     * Partially updates an {@link EvaluationEntity}.
     * <p>
     * The fields concerned by this update operation are: {@code evaluationDate},
     * {@code supervisorRating}, {@code supervisorFeedback} and {@code status}.
     * Though not all those fields are required during a single update operation.
     * 
     * @param evaluationId
     * @param jsonPatch
     * 
     * @return the updated evaluation.
     * 
     * @throws IllegalArgumentException if the evaluation to be updated have a 
     * {@code CLOSED} status, or if the JSON patch application fails, or if the 
     * specified evaluation date is in the past, or if the specified supervisor 
     * rating is not a number between
     * {@link com.talan.polaris.constants.CommonConstants#EVALUATION_RATING_MIN} and
     * {@link com.talan.polaris.constants.CommonConstants#EVALUATION_RATING_MAX}, or 
     * if the specified supervisor feedback length exceeds 
     * {@link com.talan.polaris.constants.CommonConstants#EVALUATION_FEEDBACK_MAX_LENGTH},
     * or if there is an attempt to close the evaluation while the supervisor  
     * rating and feedback are not setted.
     */
    public EvaluationEntity partialUpdateEvaluation(
            String evaluationId,
            JsonPatch jsonPatch);

}
