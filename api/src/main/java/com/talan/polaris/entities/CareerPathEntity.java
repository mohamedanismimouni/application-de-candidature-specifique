package com.talan.polaris.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * A mapped entity representing the career path domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(
    name = "CAREER_PATHS",
    uniqueConstraints = @UniqueConstraint(
        columnNames = { "FROM_CAREER_STEP", "TO_CAREER_STEP" }
    )
)
public class CareerPathEntity extends GenericEntity {

    private static final long serialVersionUID = -746721472270776415L;

    @NotNull
    @Min(value = 1)
    @Column(nullable = false)
    private int yearsOfExperience;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "FROM_CAREER_STEP")
    private CareerStepEntity fromCareerStep;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "TO_CAREER_STEP")
    private CareerStepEntity toCareerStep;

    public CareerPathEntity() {
        super();
    }

    public CareerPathEntity(
            int yearsOfExperience,
            CareerStepEntity fromCareerStep,
            CareerStepEntity toCareerStep) {

        super();
        this.yearsOfExperience = yearsOfExperience;
        this.fromCareerStep = fromCareerStep;
        this.toCareerStep = toCareerStep;

    }

    public CareerPathEntity(
            String id,
            Instant createdAt,
            Instant updatedAt,
            int yearsOfExperience,
            CareerStepEntity fromCareerStep,
            CareerStepEntity toCareerStep) {

        super(id, createdAt, updatedAt);
        this.yearsOfExperience = yearsOfExperience;
        this.fromCareerStep = fromCareerStep;
        this.toCareerStep = toCareerStep;

    }

    public int getYearsOfExperience() {
        return this.yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public CareerStepEntity getFromCareerStep() {
        return this.fromCareerStep;
    }

    public void setFromCareerStep(CareerStepEntity fromCareerStep) {
        this.fromCareerStep = fromCareerStep;
    }

    public CareerStepEntity getToCareerStep() {
        return this.toCareerStep;
    }

    public void setToCareerStep(CareerStepEntity toCareerStep) {
        this.toCareerStep = toCareerStep;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("CareerPathEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("yearsOfExperience = ").append(this.getYearsOfExperience()).append("\n")
                .append("fromCareerStep    = ").append(this.getFromCareerStep()).append("\n")
                .append("toCareerStep      = ").append(this.getToCareerStep())
                .toString();

    }

}
