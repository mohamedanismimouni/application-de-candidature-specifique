package com.talan.polaris.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.talan.polaris.enumerations.QuestionTypeEnum;
import com.talan.polaris.enumerations.SurveyTypeEnum;
import com.talan.polaris.utils.validation.constraints.QuestionChoiceConstraint;

/**
 * A mapped entity representing a super model for all different choice 
 * questions domain models, a subtype of the close ended question 
 * domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@QuestionChoiceConstraint
public class ChoiceQuestionEntity extends CloseEndedQuestionEntity {

    private static final long serialVersionUID = 7953791489366628013L;

    @Column(nullable = true)
    private boolean multipleChoices;

    @NotNull
    @Size(min = 2)
    @Valid
    @OneToMany(
        mappedBy = "question",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private Collection<ChoiceEntity> choices;

    public ChoiceQuestionEntity() {
        super();
    }

    public ChoiceQuestionEntity(
            String id,
            Instant createdAt,
            Instant updatedAt,
            QuestionTypeEnum questionType,
            SurveyTypeEnum surveyType,
            int position,
            String content,
            boolean enabled,
            boolean required,
            boolean multipleChoices,
            Collection<ChoiceEntity> choices) {

        super(id, createdAt, updatedAt, questionType, surveyType, position, content, enabled, required);
        this.multipleChoices = multipleChoices;
        this.choices = choices;

    }

    public boolean getMultipleChoices() {
        return this.multipleChoices;
    }

    public void setMultipleChoices(boolean multipleChoices) {
        this.multipleChoices = multipleChoices;
    }

    public Collection<ChoiceEntity> getChoices() {
        return this.choices;
    }

    public void setChoices(Collection<ChoiceEntity> choices) {
        if (this.choices == null) {
            this.choices = new ArrayList<>();
        } else {
            this.choices.clear();
        }
        if (choices != null) {
            this.choices.addAll(choices);
        }
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append(super.toString()).append("\n")
                .append("multipleChoices   = ").append(this.getMultipleChoices()).append("\n")
                .append("choices           = ").append(this.getChoices())
                .toString();

    }

}
