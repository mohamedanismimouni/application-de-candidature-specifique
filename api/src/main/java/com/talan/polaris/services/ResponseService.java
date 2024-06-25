package com.talan.polaris.services;

import java.util.Collection;

import com.talan.polaris.dto.Response;
import com.talan.polaris.entities.ResponseEntity;
import com.talan.polaris.enumerations.SurveyTypeEnum;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link ResponseEntity}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface ResponseService extends GenericService<ResponseEntity> {

    /**
     * Finds a response to a specific question given by its {@code questionId} 
     * for a collaborator given by its {@code collaboratorId}.
     * 
     * @param questionId
     * @param collaboratorId
     * 
     * @return the response matching the given parameters if any.
     */
    public ResponseEntity findResponseByQuestionIdAndCollaboratorId(
            String questionId,
            Long collaboratorId);

    /**
     * Finds responses of a collaborator given by its {@code collaboratorId} and
     * having the specified {@code surveyType}.
     * <p>
     * If {@code evaluationId} is specified, then returns the responses associated 
     * to a specific evaluation.
     * 
     * @param collaboratorId
     * @param surveyType
     * @param evaluationId   optional
     * 
     * @return the responses matching the given parameters if any.
     */
    public Collection<Response> findResponsesByCollaboratorIdAndSurveyType(
            Long collaboratorId,
            SurveyTypeEnum surveyType,
            String evaluationId);

    /**
     * Creates a response for a collaborator given by its {@code collaboratorId}.
     * <p>
     * If the response is for a choice question, then
     * {@link com.talan.polaris.entities.SelectedChoiceEntity}s will be created
     * accordingly.
     * 
     * @param response
     * @param collaboratorId
     * 
     * @return the created response.
     * 
     * @throws IllegalArgumentException if question is disabled, or if a response 
     * for the same question by the same collaborator is already submitted, or if 
     * the response is for an open ended question and the content is blank or its 
     * lenth exceeds 
     * {@link com.talan.polaris.constants.CommonConstants#OPEN_ENDED_RESPONSE_CONTENT_MAX_LENGTH} 
     * or if the response is for a rating scale question and the rating is not 
     * within the range specified by the question, or if the response is for a 
     * choice question and the selected choices are not a subset of the collection  
     * of the question's enabled choices or they are not present at all.
     */
    public Response createResponse(Response response, Long collaboratorId);

}
