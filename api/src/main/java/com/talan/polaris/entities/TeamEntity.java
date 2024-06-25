package com.talan.polaris.entities;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * A mapped entity representing the team domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(name = "TEAMS")
@Getter
@Setter
public class TeamEntity extends GenericEntity {

    private static final long serialVersionUID = 810003286247633198L;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true, columnDefinition = "character varying(7)")
    private String teamEvaluationDate;

    @NotNull
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "MANAGED_BY_ID")
    private CollaboratorEntity managedBy;

    public TeamEntity() {
        super();
    }

    public TeamEntity(
            String id,
            Instant createdAt,
            Instant updatedAt,
            String name,
            CollaboratorEntity managedBy) {

        super(id, createdAt, updatedAt);
        this.name = name;
        this.managedBy = managedBy;

    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("TeamEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("name              = ").append(this.getName()).append("\n")
                .append("evaluationDate    = ").append(this.getTeamEvaluationDate()).append("\n")
                .append("managedBy         = ").append(this.getManagedBy())
                .toString();

    }

}
