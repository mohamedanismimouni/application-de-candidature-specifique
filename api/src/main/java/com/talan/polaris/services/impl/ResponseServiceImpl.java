package com.talan.polaris.services.impl;

import com.talan.polaris.dto.Response;

import com.talan.polaris.entities.*;
import com.talan.polaris.enumerations.EvaluationStatusEnum;
import com.talan.polaris.enumerations.QuestionTypeEnum;
import com.talan.polaris.enumerations.ResponseTypeEnum;
import com.talan.polaris.enumerations.SurveyTypeEnum;
import com.talan.polaris.repositories.ResponseRepository;
import com.talan.polaris.services.EvaluationService;
import com.talan.polaris.services.QuestionService;
import com.talan.polaris.services.ResponseService;
import com.talan.polaris.services.SelectedChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.talan.polaris.constants.CommonConstants.OPEN_ENDED_RESPONSE_CONTENT_MAX_LENGTH;

import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_ENABLED_QUESTION_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_UNIQUE_RESPONSE_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_CONTENT_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_RATING_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_CHOICES_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_SINGLE_CHOICE_CONSTRAINT;

/**
 * An implementation of {@link ResponseService}, containing business methods
 * implementations specific to {@link ResponseEntity}, and may override some of
 * the common methods' implementations inherited from
 * {@link GenericServiceImpl}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class ResponseServiceImpl
        extends GenericServiceImpl<ResponseEntity>
        implements ResponseService {

    private final ResponseRepository responseRepository;
    private final QuestionService questionService;
    private final SelectedChoiceService selectedChoiceService;
    private final EvaluationService evaluationService;

    @Autowired
    public ResponseServiceImpl(
            ResponseRepository repository,
            QuestionService questionService,
            SelectedChoiceService selectedChoiceService,
            EvaluationService evaluationService) {

        super(repository);
        this.responseRepository = repository;
        this.questionService = questionService;
        this.selectedChoiceService = selectedChoiceService;
        this.evaluationService = evaluationService;

    }

    @Override
    public ResponseEntity findResponseByQuestionIdAndCollaboratorId(
            String questionId,
            Long collaboratorId) {

        return this.responseRepository.findResponseByQuestionIdAndCollaboratorId(
                questionId,
                collaboratorId);

    }

    @Override
    public Collection<Response> findResponsesByCollaboratorIdAndSurveyType(
            Long collaboratorId,
            SurveyTypeEnum surveyType,
            String evaluationId) {

        return this.responseRepository.findResponsesByCollaboratorIdAndSurveyType(
                collaboratorId,
                surveyType,
                evaluationId)
                        .stream()
                        .map((ResponseEntity responseEntity) -> {

                            if (responseEntity.getResponseType().equals(ResponseTypeEnum.OPEN_ENDED) ||
                                    responseEntity.getResponseType().equals(ResponseTypeEnum.RATING_SCALE)) {

                                return Response.fromResponseEntity(responseEntity, null);

                            } else {

                                return Response.fromResponseEntity(
                                        responseEntity,
                                        this.selectedChoiceService.findChoicesByResponseId(responseEntity.getId()));

                            }

                        })
                        .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public Response createResponse(Response response, Long collaboratorId) {

        QuestionEntity question = this.questionService.findById(response.getQuestion().getId());

        ResponseEntity createdResponse = null;
        Response returnedResponse = null;

        CollaboratorEntity collaborator = new CollaboratorEntity();
        collaborator.setId(collaboratorId);

        if (!question.isEnabled()) {

            throw new IllegalArgumentException(
                    ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_ENABLED_QUESTION_CONSTRAINT);

        }

        if (!question.getSurveyType().equals(SurveyTypeEnum.EVALUATION)) {

            if (this.findResponseByQuestionIdAndCollaboratorId(
                    question.getId(),
                    collaboratorId) != null) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_UNIQUE_RESPONSE_CONSTRAINT);

            }

        } else {

            if (response.getEvaluation() == null) {
                throw new IllegalArgumentException();
            }

            EvaluationEntity evaluation = this.evaluationService.findById(response.getEvaluation().getId());

            if (evaluation.getStatus().equals(EvaluationStatusEnum.CLOSED) ||
                    evaluation.getEvaluationDate().isAfter(LocalDate.now())) {

                throw new IllegalArgumentException();

            }

        }

        switch (question.getQuestionType()) {

            case OPEN_ENDED:

                if (response.getContent() == null ||
                        response.getContent().trim().isEmpty() ||
                        response.getContent().length() > OPEN_ENDED_RESPONSE_CONTENT_MAX_LENGTH) {

                    throw new IllegalArgumentException(
                            ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_CONTENT_CONSTRAINT);

                }

                createdResponse = new OpenEndedResponseEntity(question, collaborator, response.getEvaluation(), response.getContent());

                create(createdResponse);

                returnedResponse = Response.fromResponseEntity(createdResponse, null);

                break;

            case RATING_SCALE:

                RatingScaleQuestionEntity ratingScaleQuestion = (RatingScaleQuestionEntity) question;
                if (response.getRating() == null ||
                        response.getRating() < ratingScaleQuestion.getLowestValue() ||
                        response.getRating() > ratingScaleQuestion.getHighestValue()) {

                    throw new IllegalArgumentException(
                            ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_RATING_CONSTRAINT);

                }

                createdResponse = new RatingScaleResponseEntity(question, collaborator, response.getEvaluation(), response.getRating());

                create(createdResponse);

                returnedResponse = Response.fromResponseEntity(createdResponse, null);

                break;

            case REGULAR_CHOICE:
            case QUIZ_CHOICE:

                if (response.getChoices() == null || response.getChoices().isEmpty()) {

                    throw new IllegalArgumentException(
                            ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_CHOICES_CONSTRAINT);

                }

                ChoiceQuestionEntity choiceQuestion = (ChoiceQuestionEntity) question;
                Collection<ChoiceEntity> enabledChoices = choiceQuestion.getChoices()
                        .stream()
                        .filter(choice -> choice.isEnabled())
                        .collect(Collectors.toList());

                if (enabledChoices == null || enabledChoices.isEmpty()) {

                    throw new IllegalArgumentException(
                            ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_CHOICES_CONSTRAINT);

                }

                for (ChoiceEntity choice : response.getChoices()) {

                    if (enabledChoices.stream().noneMatch(c -> c.getId().equals(choice.getId()))) {

                        throw new IllegalArgumentException(
                                ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_CHOICES_CONSTRAINT);

                    }

                }

                if (!choiceQuestion.getMultipleChoices() && response.getChoices().size() > 1) {

                    throw new IllegalArgumentException(
                            ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_SINGLE_CHOICE_CONSTRAINT);

                }

                if (question.getQuestionType().equals(QuestionTypeEnum.REGULAR_CHOICE)) {
                    createdResponse = new RegularChoiceResponseEntity(question, collaborator, response.getEvaluation());
                }
                if (question.getQuestionType().equals(QuestionTypeEnum.QUIZ_CHOICE)) {
                    createdResponse = new QuizChoiceResponseEntity(question, collaborator, response.getEvaluation());
                }

                create(createdResponse);

                for (ChoiceEntity choice : response.getChoices()) {

                    this.selectedChoiceService.create(new SelectedChoiceEntity(
                            (ChoiceResponseEntity) createdResponse,
                            choice));

                }

                returnedResponse = Response.fromResponseEntity(createdResponse, response.getChoices());

                break;

        }

        return returnedResponse;

    }

}
