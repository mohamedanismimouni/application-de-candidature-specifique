package com.talan.polaris.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * A mapped entity representing the career step domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(name = "CAREER_STEPS")
public class CareerStepEntity extends GenericEntity {

    private static final long serialVersionUID = 4540615192046560147L;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String label;

    public CareerStepEntity() {
        super();
    }

    public CareerStepEntity(
            String id,
            Instant createdAt,
            Instant updatedAt,
            String label) {

        super(id, createdAt, updatedAt);
        this.label = label;

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
                .append("\n").append("CareerStepEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("label             = ").append(this.getLabel())
                .toString();

    }

}
