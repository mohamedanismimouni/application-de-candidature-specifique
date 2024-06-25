package com.talan.polaris.entities;

import java.time.Instant;
import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.talan.polaris.enumerations.QuestionTypeEnum;
import com.talan.polaris.enumerations.SurveyTypeEnum;

import static com.talan.polaris.enumerations.QuestionTypeEnum.QuestionTypeConstants.REGULAR_CHOICE_QUESTION_TYPE;

/**
 * A mapped entity representing a regular choice question domain model, 
 * a subtype of the choice question domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue(REGULAR_CHOICE_QUESTION_TYPE)
public class RegularChoiceQuestionEntity extends ChoiceQuestionEntity {

    private static final long serialVersionUID = 2911612278516397169L;

    public RegularChoiceQuestionEntity() {
        super();
    }

    public RegularChoiceQuestionEntity(
            String id,
            Instant createdAt,
            Instant updatedAt,
            SurveyTypeEnum surveyType,
            int position,
            String content,
            boolean enabled,
            boolean required,
            boolean multipleChoices,
            Collection<ChoiceEntity> choices) {

        super(id, createdAt, updatedAt, QuestionTypeEnum.REGULAR_CHOICE, surveyType, position, content, enabled, required, multipleChoices, choices);

    }

}
