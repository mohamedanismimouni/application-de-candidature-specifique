package com.talan.polaris.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import com.talan.polaris.enumerations.ChoiceTypeEnum;

import static com.talan.polaris.enumerations.ChoiceTypeEnum.ChoiceTypeConstants.QUIZ_CHOICE_TYPE;

/**
 * A mapped entity representing a quiz choice domain model, 
 * a subtype of the choice domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue(QUIZ_CHOICE_TYPE)
public class QuizChoiceEntity extends ChoiceEntity {

    private static final long serialVersionUID = 6181842057357917557L;

    @Column(nullable = true)
    private boolean valid;

    public QuizChoiceEntity() {
        super();
    }

    public QuizChoiceEntity(
            String id,
            Instant createdAt,
            Instant updatedAt,
            int position,
            String content,
            boolean enabled,
            boolean valid,
            ChoiceQuestionEntity question) {

        super(id, createdAt, updatedAt, ChoiceTypeEnum.QUIZ, position, content, enabled, question);
        this.valid = valid;

    }

    @JsonIgnore
    public boolean getValid() {
        return this.valid;
    }

    @JsonSetter
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append(super.toString()).append("\n")
                .append("valid             = ").append(this.getValid())
                .toString();

    }

}
