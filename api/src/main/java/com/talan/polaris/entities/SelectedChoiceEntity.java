package com.talan.polaris.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * A mapped entity representing a selected choice domain model 
 * for a given response.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(
    name = "SELECTED_CHOICES",
    uniqueConstraints = @UniqueConstraint(
        columnNames = { "RESPONSE", "CHOICE" }
    )
)
public class SelectedChoiceEntity extends GenericEntity {

    private static final long serialVersionUID = 3755804706673092630L;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "RESPONSE")
    private ChoiceResponseEntity response;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "CHOICE")
    private ChoiceEntity choice;

    public SelectedChoiceEntity() {
        super();
    }

    public SelectedChoiceEntity(
            ChoiceResponseEntity response,
            ChoiceEntity choice) {

        super();
        this.response = response;
        this.choice = choice;

    }

    public ChoiceResponseEntity getResponse() {
        return this.response;
    }

    public void setResponse(ChoiceResponseEntity response) {
        this.response = response;
    }

    public ChoiceEntity getChoice() {
        return this.choice;
    }

    public void setChoice(ChoiceEntity choice) {
        this.choice = choice;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("SelectedChoiceEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("response          = ").append(this.getResponse()).append("\n")
                .append("choice            = ").append(this.getChoice())
                .toString();

    }

}
