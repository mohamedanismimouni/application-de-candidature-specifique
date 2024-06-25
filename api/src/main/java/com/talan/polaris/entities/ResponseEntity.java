package com.talan.polaris.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import com.talan.polaris.enumerations.ResponseTypeEnum;

import static com.talan.polaris.enumerations.ResponseTypeEnum.ResponseTypeConstants.*;

/**
 * A mapped entity representing a super model for all different 
 * responses domain models.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(name = "RESPONSES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DC")
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.PROPERTY, 
    property = "responseType", 
    visible = true
)
@JsonSubTypes({
    @Type(
        name = OPEN_ENDED_RESPONSE_TYPE, 
        value = OpenEndedResponseEntity.class
    ),
    @Type(
        name = RATING_SCALE_RESPONSE_TYPE, 
        value = RatingScaleResponseEntity.class
    ),
    @Type(
        name = REGULAR_CHOICE_RESPONSE_TYPE, 
        value = RegularChoiceResponseEntity.class
    ),
    @Type(
        name = QUIZ_CHOICE_RESPONSE_TYPE, 
        value = QuizChoiceResponseEntity.class
    )
})
public class ResponseEntity extends GenericEntity {

    private static final long serialVersionUID = -1251913992685812323L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResponseTypeEnum responseType;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "QUESTION")
    private QuestionEntity question;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "COLLABORATOR_ID")
    private CollaboratorEntity collaborator;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "EVALUATION")
    private EvaluationEntity evaluation;

    public ResponseEntity() {
        super();
    }

    public ResponseEntity(
            ResponseTypeEnum responseType,
            QuestionEntity question,
            CollaboratorEntity collaborator,
            EvaluationEntity evaluation) {

        this.responseType = responseType;
        this.question = question;
        this.collaborator = collaborator;
        this.evaluation = evaluation;

    }

    public ResponseTypeEnum getResponseType() {
        return this.responseType;
    }

    public void setResponseType(ResponseTypeEnum responseType) {
        this.responseType = responseType;
    }

    public QuestionEntity getQuestion() {
        return this.question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    public CollaboratorEntity  getCollaborator() {
        return this.collaborator;
    }

    public void setCollaborator(CollaboratorEntity  collaborator) {
        this.collaborator = collaborator;
    }

    public EvaluationEntity getEvaluation() {
        return this.evaluation;
    }

    public void setEvaluation(EvaluationEntity evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("ResponseEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("responseType      = ").append(this.getResponseType()).append("\n")
                .append("question          = ").append(this.getQuestion()).append("\n")
                .append("collaborator      = ").append(this.getCollaborator()).append("\n")
                .append("evaluation        = ").append(this.getEvaluation())
                .toString();

    }

}
