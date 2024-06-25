package com.talan.polaris.services;

import java.util.Collection;
import java.util.Optional;

import com.talan.polaris.entities.ChoiceEntity;
import com.talan.polaris.entities.QuestionEntity;
import com.talan.polaris.entities.QuizChoiceEntity;
import com.talan.polaris.enumerations.SurveyTypeEnum;
import com.talan.polaris.exceptions.ResourceNotFoundException;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link CareerStepEntity}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface QuestionService extends GenericService<QuestionEntity> {

    /**
     * Finds questions having the given {@code surveyType} and the 
     * {@code enabled} status if provided.
     * <p>
     * If the {@code enabled} status is specified, then only questions matching 
     * that status will be returned, moreover, if the question is a subtype of 
     * {@link com.talan.polaris.entities.ChoiceQuestionEntity} then the embeded
     * choices collection will contain only choices having the same 
     * {@code enabled} as specified.
     * 
     * @param surveyType of each returned question.
     * @param enabled status of each returned question and their choices.
     * 
     * @return the questions matching the given parameters if any.
     */
    public Collection<QuestionEntity> findQuestionsBySurveyTypeAndEnabledStatus(
            SurveyTypeEnum surveyType,
            Optional<Boolean> enabled);

    /**
     * Finds quiz choices associated to a question with the given {@code questionId}
     * and having the given {@code valid} status.
     * 
     * @param questionId
     * @param valid
     * 
     * @return the choices matching the given parameter if any.
     */
    public Collection<QuizChoiceEntity> findQuizChoicesByValidStatus(
            String questionId,
            boolean valid);

    /**
     * Creates the given question, after setting its {@code enabled} status to
     * {@code true} and calculating its {@code position} based on the number of
     * existing enabled questions with the same {@code surveyType} as this
     * {@code questionEntity}.
     * <p>
     * If this {@code questionEntity} is a subtype of
     * {@link com.talan.polaris.entities.ChoiceQuestionEntity} then its choices 
     * will be set as enabled and their positions will be the same as their 
     * order in the collection.
     * 
     * @param questionEntity to be created.
     * 
     * @return the created question.
     */
    public QuestionEntity createQuestion(QuestionEntity questionEntity);

    /**
     * Adds a choice to the collection of choices of an existing subtype of
     * {@link com.talan.polaris.entities.ChoiceQuestionEntity} question.
     * <p>
     * The choice will have the last position as an enabled choice, and will 
     * be set to enabled.
     * 
     * @param questionId to which the choice will be added.
     * @param choiceEntity to be added.
     * 
     * @return the created choice.
     * 
     * @throws IllegalArgumentException if the question found by the given 
     * {@code questionId} is not a subtype of 
     * {@link com.talan.polaris.entities.ChoiceQuestionEntity}.
     * @throws ConstraintViolationException if the choice type is not 
     * compatible with the question type.
     */
    public ChoiceEntity createChoice(String questionId, ChoiceEntity choiceEntity);

    /**
     * Deletes a question given by its {@code questionId}.
     * <p>
     * Every question with the same {@code surveyType} as the question to be 
     * deleted and having a greater position will be deicremented subsequently.
     * 
     * @param questionId
     */
    public void deleteQuestion(String questionId);

    /**
     * Removes a choice given by its {@code choiceId} from the list of choices 
     * of a question given by its {@code questionId}.
     * <p>
     * Every choice from the question's choices collection having a position 
     * greater than the choice to be deleted will be deicremented subsequently.
     * 
     * @param questionId
     * @param choiceId
     * 
     * @throws IllegalArgumentException if the question found by the given 
     * {@code questionId} is not a subtype of 
     * {@link com.talan.polaris.entities.ChoiceQuestionEntity}, or if the 
     * question have less than 3 enabled choices.
     * @throws ResourceNotFoundException if the question does not have the 
     * requested choice given by its {@code choiceId}.
     */
    public void deleteChoice(String questionId, String choiceId);

}
