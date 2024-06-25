package com.talan.polaris.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.talan.polaris.enumerations.SkillLevelEnum;

/**
 * A mapped entity representing the acquired skill levels domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(name = "ACQUIRED_SKILL_LEVELS")
public class AcquiredSkillLevelEntity
        extends GenericEntity
        implements Comparable<AcquiredSkillLevelEntity> {

    private static final long serialVersionUID = 4516425641576094586L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillLevelEnum level;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "ACQUIRED_SKILL")
    private AcquiredSkillEntity acquiredSkill;

    public AcquiredSkillLevelEntity() {
        super();
    }

    public SkillLevelEnum getLevel() {
        return this.level;
    }

    public void setLevel(SkillLevelEnum level) {
        this.level = level;
    }

    @JsonIgnore
    public AcquiredSkillEntity getAcquiredSkill() {
        return this.acquiredSkill;
    }

    public void setAcquiredSkill(AcquiredSkillEntity acquiredSkill) {
        this.acquiredSkill = acquiredSkill;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("AcquiredSkillLevelEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("level             = ").append(this.getLevel())
                .toString();

    }

    @Override
    public int compareTo(AcquiredSkillLevelEntity o) {

        if (this.getCreatedAt() != null && o.getCreatedAt() != null)
            return this.getCreatedAt().compareTo(o.getCreatedAt());

        return 0;

    }

}
