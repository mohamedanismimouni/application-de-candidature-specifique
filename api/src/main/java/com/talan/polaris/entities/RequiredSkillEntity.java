package com.talan.polaris.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.talan.polaris.enumerations.SkillLevelEnum;
import com.talan.polaris.enumerations.SkillWeightEnum;

/**
 * A mapped entity representing the required skills domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(
    name = "REQUIRED_SKILLS",
    uniqueConstraints = @UniqueConstraint(
        columnNames = { "PROFILE", "SKILL" }
    )
)
public class RequiredSkillEntity
        extends GenericEntity
        implements Comparable<RequiredSkillEntity> {

    private static final long serialVersionUID = -483872701695429546L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillLevelEnum level;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillWeightEnum weight;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "PROFILE")
    private ProfileEntity profile;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "SKILL")
    private SkillEntity skill;

    public RequiredSkillEntity() {
        super();
    }

    public SkillLevelEnum getLevel() {
        return this.level;
    }

    public void setLevel(SkillLevelEnum level) {
        this.level = level;
    }

    public SkillWeightEnum getWeight() {
        return this.weight;
    }

    public void setWeight(SkillWeightEnum weight) {
        this.weight = weight;
    }

    public ProfileEntity getProfile() {
        return this.profile;
    }

    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

    public SkillEntity getSkill() {
        return this.skill;
    }

    public void setSkill(SkillEntity skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("RequiredSkillEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("level             = ").append(this.getLevel()).append("\n")
                .append("weight            = ").append(this.getWeight()).append("\n")
                .append("profile           = ").append(this.getProfile()).append("\n")
                .append("skill             = ").append(this.getSkill())
                .toString();

    }

    @Override
    public int compareTo(RequiredSkillEntity o) {
        final int skillWeightComparison = o.getWeight().getSkillWeight().compareTo(this.getWeight().getSkillWeight());
        if (skillWeightComparison == 0)
            return o.getLevel().getSkillLevel().compareTo(this.getLevel().getSkillLevel());
        return skillWeightComparison;
    }

}
