package com.talan.polaris.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.talan.polaris.enumerations.QuestionTypeEnum;
import com.talan.polaris.enumerations.SurveyTypeEnum;
import com.talan.polaris.utils.validation.constraints.SurveyQuestionConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

import static com.talan.polaris.enumerations.QuestionTypeEnum.QuestionTypeConstants.*;

/**
 * A mapped entity representing a super model for all different
 * questions domain models.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(
        name = "QUESTIONS",
        uniqueConstraints = @UniqueConstraint(
                columnNames = { "questionType", "surveyType", "content" }
        )
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DC")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "questionType",
        visible = true
)
@JsonSubTypes({
        @Type(
                name = OPEN_ENDED_QUESTION_TYPE,
                value = OpenEndedQuestionEntity.class
        ),
        @Type(
                name = RATING_SCALE_QUESTION_TYPE,
                value = RatingScaleQuestionEntity.class
        ),
        @Type(
                name = REGULAR_CHOICE_QUESTION_TYPE,
                value = RegularChoiceQuestionEntity.class
        ),
        @Type(
                name = QUIZ_CHOICE_QUESTION_TYPE,
                value = QuizChoiceQuestionEntity.class
        )
})
@SurveyQuestionConstraint
@Getter
@Setter
@NoArgsConstructor
public class QuestionEntity extends GenericEntity {

    private static final long serialVersionUID = -5243185915127018466L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionTypeEnum questionType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SurveyTypeEnum surveyType;

    @Column(nullable = false)
    private int position;

    @NotBlank
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private boolean required;

    public QuestionEntity(String id, Instant createdAt, Instant updatedAt, @NotNull QuestionTypeEnum questionType, @NotNull SurveyTypeEnum surveyType, int position, @NotBlank String content, boolean enabled, boolean required) {
        super(id, createdAt, updatedAt);
        this.questionType = questionType;
        this.surveyType = surveyType;
        this.position = position;
        this.content = content;
        this.enabled = enabled;
        this.required = required;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("QuestionEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("questionType      = ").append(this.getQuestionType()).append("\n")
                .append("surveyType        = ").append(this.getSurveyType()).append("\n")
                .append("position          = ").append(this.getPosition()).append("\n")
                .append("content           = ").append(this.getContent()).append("\n")
                .append("enabled           = ").append(this.isEnabled()).append("\n")
                .append("required          = ").append(this.isRequired())
                .toString();

    }

}
