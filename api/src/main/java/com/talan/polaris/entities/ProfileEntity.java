package com.talan.polaris.entities;

import lombok.Getter;
import lombok.Setter;


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
 * A mapped entity representing the profile domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(
    name = "PROFILES",
    uniqueConstraints = @UniqueConstraint(
        columnNames = { "label", "TEAM", "CAREER_STEP" }
    )
)
@Getter
@Setter
public class ProfileEntity extends GenericEntity {

    private static final long serialVersionUID = 3963339329759102467L;

    @NotBlank
    @Column(nullable = false)
    private String label;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "TEAM")
    private TeamEntity team;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "CAREER_STEP")
    private CareerStepEntity careerStep;

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("ProfileEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("label             = ").append(this.getLabel()).append("\n")
                .append("team              = ").append(this.getTeam()).append("\n")
                .append("careerStep        = ").append(this.getCareerStep())
                .toString();

    }

}
