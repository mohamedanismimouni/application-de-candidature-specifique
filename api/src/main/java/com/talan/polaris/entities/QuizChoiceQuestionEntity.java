package com.talan.polaris.entities;

import java.time.Instant;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Positive;

import com.talan.polaris.enumerations.QuestionTypeEnum;
import com.talan.polaris.enumerations.SurveyTypeEnum;

import static com.talan.polaris.enumerations.QuestionTypeEnum.QuestionTypeConstants.QUIZ_CHOICE_QUESTION_TYPE;

/**
 * A mapped entity representing a quiz choice question domain model, 
 * a subtype of the choice question domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue(QUIZ_CHOICE_QUESTION_TYPE)
public class QuizChoiceQuestionEntity extends ChoiceQuestionEntity {

    private static final long serialVersionUID = 249315393285192788L;

    @Positive
    @Column(nullable = true)
    private int score;

    public QuizChoiceQuestionEntity() {
        super();
    }

    public QuizChoiceQuestionEntity(
            String id,
            Instant createdAt,
            Instant updatedAt,
            SurveyTypeEnum surveyType,
            int position,
            String content,
            boolean enabled,
            boolean required,
            boolean multipleChoices,
            int score,
            Collection<ChoiceEntity> choices) {

        super(id, createdAt, updatedAt, QuestionTypeEnum.QUIZ_CHOICE, surveyType, position, content, enabled, required, multipleChoices, choices);
        this.score = score;

    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append(super.toString()).append("\n")
                .append("score             = ").append(this.getScore())
                .toString();

    }

}
