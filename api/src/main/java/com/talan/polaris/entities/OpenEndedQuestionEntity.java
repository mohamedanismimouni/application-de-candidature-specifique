package com.talan.polaris.entities;

import java.time.Instant;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.talan.polaris.enumerations.QuestionTypeEnum;
import com.talan.polaris.enumerations.SurveyTypeEnum;

import static com.talan.polaris.enumerations.QuestionTypeEnum.QuestionTypeConstants.OPEN_ENDED_QUESTION_TYPE;

/**
 * A mapped entity representing an open ended question domain model, 
 * a subtype of the question domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue(OPEN_ENDED_QUESTION_TYPE)
public class OpenEndedQuestionEntity extends QuestionEntity {

    private static final long serialVersionUID = 5817160161038100295L;

    public OpenEndedQuestionEntity() {
        super();
    }

    public OpenEndedQuestionEntity(
            String id,
            Instant createdAt,
            Instant updatedAt,
            SurveyTypeEnum surveyType,
            int position,
            String content,
            boolean enabled,
            boolean required) {

        super(id, createdAt, updatedAt, QuestionTypeEnum.OPEN_ENDED, surveyType, position, content, enabled, required);

    }

}
