package com.talan.polaris.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * A mapped entity representing the onboarding domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(
    name = "ONBOARDINGS",
    uniqueConstraints = @UniqueConstraint(
        columnNames = { "SECRET_WORD_PART_HOLDER_ID", "FRESH_RECRUIT_ID" }
    )
)
public class OnboardingEntity extends GenericEntity {

    private static final long serialVersionUID = 2828750985103634997L;

    @NotBlank
    @Column(nullable = false)
    private String secretWordPart;

    @Column(nullable = true)
    private Integer rating;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "SECRET_WORD_PART_HOLDER_ID")
    private CollaboratorEntity secretWordPartHolder;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "FRESH_RECRUIT_ID")
    private CollaboratorEntity freshRecruit;

    public OnboardingEntity() {
        super();
    }

    public OnboardingEntity(
            String secretWordPart,
            CollaboratorEntity secretWordPartHolder,
            CollaboratorEntity freshRecruit) {

        super();
        this.secretWordPart = secretWordPart;
        this.secretWordPartHolder = secretWordPartHolder;
        this.freshRecruit = freshRecruit;

    }

    public String getSecretWordPart() {
        return this.secretWordPart;
    }

    public void setSecretWordPart(String secretWordPart) {
        this.secretWordPart = secretWordPart;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public CollaboratorEntity getSecretWordPartHolder() {
        return this.secretWordPartHolder;
    }

    public void setSecretWordPartHolder(CollaboratorEntity secretWordPartHolder) {
        this.secretWordPartHolder = secretWordPartHolder;
    }

    public CollaboratorEntity getFreshRecruit() {
        return this.freshRecruit;
    }

    public void setFreshRecruit(CollaboratorEntity  freshRecruit) {
        this.freshRecruit = freshRecruit;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("OnboardingEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("secretWordPart    = ").append(this.getSecretWordPart()).append("\n")
                .append("rating            = ").append(this.getRating()).append("\n")
                .append("partHolder        = ").append(this.getSecretWordPartHolder()).append("\n")
                .append("freshRecruit      = ").append(this.getFreshRecruit())
                .toString();

    }

}
