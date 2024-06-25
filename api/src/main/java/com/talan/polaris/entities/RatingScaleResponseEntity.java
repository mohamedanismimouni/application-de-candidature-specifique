package com.talan.polaris.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.talan.polaris.enumerations.ResponseTypeEnum;

import static com.talan.polaris.enumerations.ResponseTypeEnum.ResponseTypeConstants.RATING_SCALE_RESPONSE_TYPE;

/**
 * A mapped entity representing a rating scale response domain model, 
 * a subtype of the close ended response domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue(RATING_SCALE_RESPONSE_TYPE)
public class RatingScaleResponseEntity extends CloseEndedResponseEntity {

    private static final long serialVersionUID = 5096881062313402379L;

    @NotNull
    @Column(nullable = true)
    private int rating;

    public RatingScaleResponseEntity() {
        super();
    }

    public RatingScaleResponseEntity(
            QuestionEntity question,
            CollaboratorEntity collaborator,
            EvaluationEntity evaluation,
            int rating) {

        super(ResponseTypeEnum.RATING_SCALE, question, collaborator, evaluation);
        this.rating = rating;

    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append(super.toString()).append("\n")
                .append("rating            = ").append(this.getRating())
                .toString();

    }

}
