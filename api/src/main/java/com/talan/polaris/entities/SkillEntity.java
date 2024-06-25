package com.talan.polaris.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.talan.polaris.enumerations.SkillTypeEnum;

/**
 * A mapped entity representing the skill domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(name = "SKILLS")
public class SkillEntity extends GenericEntity {

    private static final long serialVersionUID = -2377686000384346740L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillTypeEnum skillType;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String label;

    public SkillEntity() {
        super();
    }

    public SkillEntity(
            String id,
            Instant createdAt,
            Instant updatedAt,
            SkillTypeEnum skillType,
            String label) {

        super(id, createdAt, updatedAt);
        this.skillType = skillType;
        this.label = label;

    }

    public SkillTypeEnum getSkillType() {
        return this.skillType;
    }

    public void setSkillType(SkillTypeEnum skillType) {
        this.skillType = skillType;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("SkillEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("skillType         = ").append(this.getSkillType()).append("\n")
                .append("label             = ").append(this.getLabel())
                .toString();

    }

}
