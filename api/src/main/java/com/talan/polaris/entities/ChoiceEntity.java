package com.talan.polaris.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.talan.polaris.enumerations.ChoiceTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

import static com.talan.polaris.enumerations.ChoiceTypeEnum.ChoiceTypeConstants.QUIZ_CHOICE_TYPE;
import static com.talan.polaris.enumerations.ChoiceTypeEnum.ChoiceTypeConstants.REGULAR_CHOICE_TYPE;

/**
 * A mapped entity representing a super model for all different 
 * choices domain models.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(
    name = "CHOICES",
    uniqueConstraints = @UniqueConstraint(
        columnNames = { "content", "QUESTION" }
    )
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DC")
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.PROPERTY, 
    property = "choiceType", 
    visible = true
)
@JsonSubTypes({
    @Type(
        name = REGULAR_CHOICE_TYPE, 
        value = RegularChoiceEntity.class
    ),
    @Type(
        name = QUIZ_CHOICE_TYPE, 
        value = QuizChoiceEntity.class
    )
})
@Getter
@Setter
@NoArgsConstructor
public class ChoiceEntity
        extends GenericEntity 
        implements Comparable<ChoiceEntity> {

    private static final long serialVersionUID = -1180779363105911089L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChoiceTypeEnum choiceType;

    @Column(nullable = false)
    private int position;

    @NotBlank
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "QUESTION")
    private ChoiceQuestionEntity question;

    @JsonIgnore
    public ChoiceQuestionEntity getQuestion() {
        return this.question;
    }

    public void setQuestion(ChoiceQuestionEntity question) {
        this.question = question;
    }

    public ChoiceEntity(String id, Instant createdAt, Instant updatedAt, @NotNull ChoiceTypeEnum choiceType, int position, @NotBlank String content, boolean enabled, ChoiceQuestionEntity question) {
        super(id, createdAt, updatedAt);
        this.choiceType = choiceType;
        this.position = position;
        this.content = content;
        this.enabled = enabled;
        this.question = question;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("ChoiceEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("choiceType        = ").append(this.getChoiceType()).append("\n")
                .append("position          = ").append(this.getPosition()).append("\n")
                .append("content           = ").append(this.getContent()).append("\n")
                .append("enabled           = ").append(this.isEnabled())
                .toString();

    }

    @Override
    public int compareTo(ChoiceEntity o) {
        return Integer.compare(this.getPosition(), o.getPosition());
    }

}
