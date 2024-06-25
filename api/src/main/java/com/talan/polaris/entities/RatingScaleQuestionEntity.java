package com.talan.polaris.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.talan.polaris.enumerations.QuestionTypeEnum;
import com.talan.polaris.enumerations.SurveyTypeEnum;
import com.talan.polaris.utils.validation.constraints.ScaleConstraint;

import static com.talan.polaris.enumerations.QuestionTypeEnum.QuestionTypeConstants.RATING_SCALE_QUESTION_TYPE;

/**
 * A mapped entity representing a rating scale question domain model, 
 * a subtype of the close ended question domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue(RATING_SCALE_QUESTION_TYPE)
@ScaleConstraint
public class RatingScaleQuestionEntity extends CloseEndedQuestionEntity {

    private static final long serialVersionUID = -8357738198068161234L;

    @Column(nullable = true)
    private int lowestValue;

    @Column(nullable = true)
    private int highestValue;

    public RatingScaleQuestionEntity() {
        super();
    }

    public RatingScaleQuestionEntity(
            String id,
            Instant createdAt,
            Instant updatedAt,
            SurveyTypeEnum surveyType,
            int position,
            String content,
            boolean enabled,
            boolean required,
            int lowestValue,
            int highestValue) {

        super(id, createdAt, updatedAt, QuestionTypeEnum.RATING_SCALE, surveyType, position, content, enabled, required);
        this.lowestValue = lowestValue;
        this.highestValue = highestValue;

    }

    public int getLowestValue() {
        return this.lowestValue;
    }

    public void setLowestValue(int lowestValue) {
        this.lowestValue = lowestValue;
    }

    public int getHighestValue() {
        return this.highestValue;
    }

    public void setHighestValue(int highestValue) {
        this.highestValue = highestValue;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append(super.toString()).append("\n")
                .append("lowestValue       = ").append(this.getLowestValue()).append("\n")
                .append("highestValue      = ").append(this.getHighestValue())
                .toString();

    }

}
