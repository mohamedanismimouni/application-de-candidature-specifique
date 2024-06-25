package com.talan.polaris.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A mapped entity representing the acquired skills domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(
    name = "ACQUIRED_SKILLS",
    uniqueConstraints = @UniqueConstraint(
        columnNames = { "COLLABORATOR_ID", "SKILL" }
    )
)
public class AcquiredSkillEntity extends GenericEntity {

    private static final long serialVersionUID = 738525124707315599L;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "COLLABORATOR_ID")
    private CollaboratorEntity  collaborator;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "SKILL")
    private SkillEntity skill;

    @NotNull
    @Size(min = 1, max = 1)
    @Valid
    @OneToMany(
        mappedBy = "acquiredSkill",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<AcquiredSkillLevelEntity> progress;

    public AcquiredSkillEntity() {
        super();
    }

    public CollaboratorEntity  getCollaborator() {
        return this.collaborator;
    }

    public void setCollaborator(CollaboratorEntity  collaborator) {
        this.collaborator = collaborator;
    }

    public SkillEntity getSkill() {
        return this.skill;
    }

    public void setSkill(SkillEntity skill) {
        this.skill = skill;
    }

    public List<AcquiredSkillLevelEntity> getProgress() {
        Collections.sort(this.progress);
        return this.progress;
    }

    public void setProgress(List<AcquiredSkillLevelEntity> progress) {
        if (this.progress == null) {
            this.progress = new ArrayList<>();
        } else {
            this.progress.clear();
        }
        if (progress != null) {
            this.progress.addAll(progress);
        }
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("AcquiredSkillEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("collaborator      = ").append(this.getCollaborator()).append("\n")
                .append("skill             = ").append(this.getSkill()).append("\n")
                .append("progress          = ").append(this.getProgress())
                .toString();

    }

}
