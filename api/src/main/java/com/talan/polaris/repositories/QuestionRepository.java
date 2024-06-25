package com.talan.polaris.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.QuestionEntity;
import com.talan.polaris.entities.QuizChoiceEntity;
import com.talan.polaris.enumerations.SurveyTypeEnum;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link QuestionEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Repository
public interface QuestionRepository extends GenericRepository<QuestionEntity> {

    @Query("SELECT question FROM QuestionEntity AS question "
            + "WHERE question.surveyType LIKE :surveyType "
            + "ORDER BY question.position ASC")
    public Collection<QuestionEntity> findQuestionsBySurveyType(
            @Param("surveyType") SurveyTypeEnum surveyType);

    @Query("SELECT COUNT(*) FROM QuestionEntity AS question "
            + "WHERE question.surveyType LIKE :surveyType "
            + "AND question.enabled = :enabled")
    public int countQuestionsWithSurveyTypeAndEnabledStatus(
            @Param("surveyType") SurveyTypeEnum surveyType,
            @Param("enabled") boolean enabled);

    @Query("SELECT question FROM QuestionEntity AS question "
            + "WHERE question.surveyType LIKE :surveyType "
            + "AND question.position > :position "
            + "ORDER BY question.position ASC")
    public Collection<QuestionEntity> findQuestionsBySurveyTypeWithPositionGreaterThan(
            @Param("surveyType") SurveyTypeEnum surveyType,
            @Param("position") int position);

    @Query("SELECT choice FROM QuizChoiceEntity AS choice "
            + "WHERE choice.question.id LIKE :questionId "
            + "AND choice.valid = :valid "
            + "ORDER BY choice.position ASC")
    public Collection<QuizChoiceEntity> findQuizChoicesByValidStatus(
            @Param("questionId") String questionId,
            @Param("valid") boolean valid);

}
