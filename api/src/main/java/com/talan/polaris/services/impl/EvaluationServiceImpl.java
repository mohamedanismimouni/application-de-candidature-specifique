package com.talan.polaris.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.json.JsonException;
import javax.json.JsonPatch;
import javax.json.JsonStructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.talan.polaris.entities.CareerPositionEntity;
import com.talan.polaris.entities.EvaluationEntity;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.enumerations.EvaluationStatusEnum;
import com.talan.polaris.repositories.EvaluationRepository;
import com.talan.polaris.services.CareerPositionService;
import com.talan.polaris.services.EvaluationService;

import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_JSON_PATCH;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_EVALUATION_CLOSED_EVALUATION_UPDATE_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_EVALUATION_EVALUATION_CURRENT_CAREER_POSITION_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_EVALUATION_UNIQUE_OPEN_EVALUATION_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_EVALUATION_EVALUATION_DATE_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_EVALUATION_RATING_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_EVALUATION_FEEDBACK_LENGTH_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_EVALUATION_CLOSING_EVALUATION_CONSTRAINT;
import static com.talan.polaris.constants.CommonConstants.EVALUATION_RATING_MIN;
import static com.talan.polaris.constants.CommonConstants.EVALUATION_RATING_MAX;
import static com.talan.polaris.constants.CommonConstants.EVALUATION_FEEDBACK_MAX_LENGTH;

/**
 * An implementation of {@link EvaluationService}, containing business methods
 * implementations specific to {@link EvaluationEntity}, and may override some
 * of the common methods' implementations inherited from
 * {@link GenericServiceImpl}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class EvaluationServiceImpl
        extends GenericServiceImpl<EvaluationEntity>
        implements EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final CareerPositionService careerPositionService;
    private final ObjectMapper objectMapper;

    @Autowired
    public EvaluationServiceImpl(
            EvaluationRepository repository,
            @Lazy CareerPositionService careerPositionService,
            ObjectMapper objectMapper) {

        super(repository);
        this.evaluationRepository = repository;
        this.careerPositionService = careerPositionService;
        this.objectMapper = objectMapper;

    }

    @Override
    public Collection<EvaluationEntity> findEvaluations(
            Long collaboratorId,
            Long supervisorId,
            EvaluationStatusEnum evaluationStatus) {

        List<EvaluationEntity> evaluations = new ArrayList<>(
                this.evaluationRepository.findEvaluations(
                        collaboratorId,
                        supervisorId,
                        evaluationStatus));

        Collections.sort(evaluations);

        return evaluations;

    }

    @Override
    public Collection<EvaluationEntity> findTeamEvaluations(
            String teamId,
            EvaluationStatusEnum evaluationStatus) {

        return this.evaluationRepository.findTeamEvaluations(
                teamId,
                evaluationStatus);

    }

    @Override
    public EvaluationEntity createEvaluation(EvaluationEntity evaluation) {

        CareerPositionEntity careerPosition = this.careerPositionService.findById(
                evaluation.getCareerPosition().getId());

        if (!(CareerPositionStatusEnum.CURRENT).equals(careerPosition.getStatus())) {

            throw new IllegalArgumentException(
                    ERROR_BAD_REQUEST_EVALUATION_EVALUATION_CURRENT_CAREER_POSITION_CONSTRAINT);

        }

        if (evaluation.getEvaluationDate().isBefore(LocalDate.now())) {

            throw new IllegalArgumentException(
                ERROR_BAD_REQUEST_EVALUATION_EVALUATION_DATE_CONSTRAINT);

        }

        Collection<EvaluationEntity> openEvaluations = this.findEvaluations(
                careerPosition.getCollaborator().getId(),
                null,
                EvaluationStatusEnum.OPEN);

        if (!openEvaluations.isEmpty()) {

            throw new IllegalArgumentException(
                    ERROR_BAD_REQUEST_EVALUATION_UNIQUE_OPEN_EVALUATION_CONSTRAINT);

        }

        evaluation.setSupervisorRating(null);
        evaluation.setSupervisorFeedback(null);
        evaluation.setStatus(EvaluationStatusEnum.OPEN);

        return create(evaluation);

    }

    @Override
    @Transactional
    public EvaluationEntity partialUpdateEvaluation(
            String evaluationId,
            JsonPatch jsonPatch) {

        EvaluationEntity evaluation = findById(evaluationId);

        if (evaluation.getStatus().equals(EvaluationStatusEnum.CLOSED)) {

            throw new IllegalArgumentException(
                    ERROR_BAD_REQUEST_EVALUATION_CLOSED_EVALUATION_UPDATE_CONSTRAINT);

        }

        JsonStructure targetJson = objectMapper.convertValue(
                new EvaluationEntity(),
                JsonStructure.class);
    
        JsonStructure patchedJson;
        try {
            patchedJson = jsonPatch.apply(targetJson);
        } catch (JsonException e) {
            throw new IllegalArgumentException(ERROR_BAD_REQUEST_JSON_PATCH, e);
        }

        EvaluationEntity patchedEvaluation = this.objectMapper.convertValue(
                patchedJson,
                EvaluationEntity.class);

        if (patchedEvaluation.getEvaluationDate() != null) {

            if (patchedEvaluation.getEvaluationDate().isBefore(LocalDate.now())) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_EVALUATION_EVALUATION_DATE_CONSTRAINT);

            } else {
                evaluation.setEvaluationDate(patchedEvaluation.getEvaluationDate());
            }

        }

        if (patchedEvaluation.getSupervisorRating() != null) {

            if (patchedEvaluation.getSupervisorRating().compareTo(EVALUATION_RATING_MIN) < 0 ||
                    patchedEvaluation.getSupervisorRating().compareTo(EVALUATION_RATING_MAX) > 0) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_EVALUATION_RATING_CONSTRAINT);

            } else {

                evaluation.setSupervisorRating(patchedEvaluation.getSupervisorRating());

            }

        }

        if (patchedEvaluation.getSupervisorFeedback() != null) {

            if (patchedEvaluation.getSupervisorFeedback().length() > EVALUATION_FEEDBACK_MAX_LENGTH) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_EVALUATION_FEEDBACK_LENGTH_CONSTRAINT);

            } else {

                evaluation.setSupervisorFeedback(patchedEvaluation.getSupervisorFeedback());

            }

        }

        if ((EvaluationStatusEnum.CLOSED).equals(patchedEvaluation.getStatus())) {

            if (evaluation.getSupervisorRating() == null ||
                    evaluation.getSupervisorFeedback() == null) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_EVALUATION_CLOSING_EVALUATION_CONSTRAINT);

            } else {
                evaluation.setStatus(EvaluationStatusEnum.CLOSED);
            }

        }

        return evaluation;

    }

}
