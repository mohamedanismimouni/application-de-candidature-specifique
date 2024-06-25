package com.talan.polaris.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import com.talan.polaris.enumerations.ResponseTypeEnum;

import static com.talan.polaris.enumerations.ResponseTypeEnum.ResponseTypeConstants.OPEN_ENDED_RESPONSE_TYPE;
import static com.talan.polaris.constants.CommonConstants.OPEN_ENDED_RESPONSE_CONTENT_MAX_LENGTH;

/**
 * A mapped entity representing an open ended response domain model, 
 * a subtype of the response domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue(OPEN_ENDED_RESPONSE_TYPE)
public class OpenEndedResponseEntity extends ResponseEntity {

    private static final long serialVersionUID = 8037250079074798985L;

    @NotBlank
    @Column(nullable = true, length = OPEN_ENDED_RESPONSE_CONTENT_MAX_LENGTH)
    private String content;

    public OpenEndedResponseEntity() {
        super();
    }

    public OpenEndedResponseEntity(
            QuestionEntity question,
            CollaboratorEntity collaborator,
            EvaluationEntity evaluation,
            String content) {

        super(ResponseTypeEnum.OPEN_ENDED, question, collaborator, evaluation);
        this.content = content;

    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append(super.toString()).append("\n")
                .append("content           = ").append(this.getContent())
                .toString();

    }

}
