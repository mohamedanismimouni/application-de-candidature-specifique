package com.talan.polaris.dto;

import java.io.Serializable;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import com.talan.polaris.entities.ChoiceEntity;
import com.talan.polaris.entities.EvaluationEntity;
import com.talan.polaris.entities.OpenEndedResponseEntity;
import com.talan.polaris.entities.QuestionEntity;
import com.talan.polaris.entities.RatingScaleResponseEntity;
import com.talan.polaris.entities.ResponseEntity;
import com.talan.polaris.enumerations.ResponseTypeEnum;

/**
 * Response.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public final class Response implements Serializable {

    private static final long serialVersionUID = 3952913560413660565L;

    private final String id;

    private final ResponseTypeEnum responseType;

    private final EvaluationEntity evaluation;

    @NotNull
    private final QuestionEntity question;

    private final String content;

    private final Integer rating;

    private final Collection<ChoiceEntity> choices;

    public Response(
            String id,
            ResponseTypeEnum responseType,
            EvaluationEntity evaluation,
            QuestionEntity question,
            String content,
            Integer rating,
            Collection<ChoiceEntity> choices) {

        this.id = id;
        this.responseType = responseType;
        this.evaluation = evaluation;
        this.question = question;
        this.content = content;
        this.rating = rating;
        this.choices = choices;

    }

    public static Response fromResponseEntity(ResponseEntity responseEntity, Collection<ChoiceEntity> choices) {

        if (responseEntity == null)
                throw new NullPointerException("Expecting a non null ResponseEntity argument");

        switch (responseEntity.getResponseType()) {

            case OPEN_ENDED:

                OpenEndedResponseEntity openEndedResponseEntity = (OpenEndedResponseEntity) responseEntity;

                return new Response(
                        openEndedResponseEntity.getId(),
                        openEndedResponseEntity.getResponseType(),
                        openEndedResponseEntity.getEvaluation(),
                        openEndedResponseEntity.getQuestion(),
                        openEndedResponseEntity.getContent(),
                        null,
                        null);

            case RATING_SCALE:

                RatingScaleResponseEntity ratingScaleResponseEntity = (RatingScaleResponseEntity) responseEntity;

                return new Response(
                        ratingScaleResponseEntity.getId(),
                        ratingScaleResponseEntity.getResponseType(),
                        ratingScaleResponseEntity.getEvaluation(),
                        ratingScaleResponseEntity.getQuestion(),
                        null,
                        ratingScaleResponseEntity.getRating(),
                        null);

            default:

                return new Response(
                        responseEntity.getId(),
                        responseEntity.getResponseType(),
                        responseEntity.getEvaluation(),
                        responseEntity.getQuestion(),
                        null,
                        null,
                        choices);

        }

    }

    public String getId() {
        return this.id;
    }

    public ResponseTypeEnum getResponseType() {
        return this.responseType;
    }

    public EvaluationEntity getEvaluation() {
        return this.evaluation;
    }

    public QuestionEntity getQuestion() {
        return this.question;
    }

    public String getContent() {
        return this.content;
    }

    public Integer getRating() {
        return this.rating;
    }

    public Collection<ChoiceEntity> getChoices() {
        return this.choices;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("Response").append("\n")
                .append("id                = ").append(this.id).append("\n")
                .append("responseType      = ").append(this.responseType).append("\n")
                .append("evaluation        = ").append(this.evaluation).append("\n")
                .append("question          = ").append(this.question).append("\n")
                .append("content           = ").append(this.content).append("\n")
                .append("rating            = ").append(this.rating).append("\n")
                .append("choices           = ").append(this.choices)
                .toString();

    }

}
