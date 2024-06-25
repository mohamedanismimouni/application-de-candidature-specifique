package com.talan.polaris.entities;

import java.time.Instant;

import javax.persistence.Entity;

import com.talan.polaris.enumerations.QuestionTypeEnum;
import com.talan.polaris.enumerations.SurveyTypeEnum;

/**
 * A mapped entity representing a super model for all different close ended 
 * questions domain models, a subtype of the question domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
public class CloseEndedQuestionEntity extends QuestionEntity {

    private static final long serialVersionUID = 8064705616006221463L;

    public CloseEndedQuestionEntity() {
        super();
    }

    public CloseEndedQuestionEntity(
            String id,
            Instant createdAt,
            Instant updatedAt,
            QuestionTypeEnum questionType,
            SurveyTypeEnum surveyType,
            int position,
            String content,
            boolean enabled,
            boolean required) {

        super(id, createdAt, updatedAt, questionType, surveyType, position, content, enabled, required);

    }

}
