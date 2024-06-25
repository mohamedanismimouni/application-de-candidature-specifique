package com.talan.polaris.entities;

import java.time.Instant;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.utils.validation.constraints.CurrentCareerPositionConstraint;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * A mapped entity representing the career position domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Setter
@Table(
    name = "CAREER_POSITIONS",
    uniqueConstraints = @UniqueConstraint(
        columnNames = { "PROFILE", "COLLABORATOR_ID", "SUPERVISOR_ID" }
    )
)
@CurrentCareerPositionConstraint
public class CareerPositionEntity {

    private static final long serialVersionUID = 8528305560507099899L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_career_position_entity")
    @SequenceGenerator(name = "seq_career_position_entity", sequenceName = "seq_career_position_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "PROFILE")
    private ProfileEntity profile;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "COLLABORATOR_ID")
    private CollaboratorEntity collaborator;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "SUPERVISOR_ID")
    private CollaboratorEntity  supervisor;

    @Column(nullable = true)
    private LocalDate startedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CareerPositionStatusEnum status;

    public CareerPositionEntity() {
        super();
    }

    public ProfileEntity getProfile() {
        return this.profile;
    }

    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

    public CollaboratorEntity  getCollaborator() {
        return this.collaborator;
    }

    public void setCollaborator(CollaboratorEntity  collaborator) {
        this.collaborator = collaborator;
    }

    public CollaboratorEntity  getSupervisor() {
        return this.supervisor;
    }

    public void setSupervisor(CollaboratorEntity  supervisor) {
        this.supervisor = supervisor;
    }

    public LocalDate getStartedAt() {
        return this.startedAt;
    }

    public void setStartedAt(LocalDate startedAt) {
        this.startedAt = startedAt;
    }

    public CareerPositionStatusEnum getStatus() {
        return this.status;
    }

    public void setStatus(CareerPositionStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("CareerPositionEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("profile           = ").append(this.getProfile()).append("\n")
                .append("collaborator      = ").append(this.getCollaborator()).append("\n")
                .append("supervisor        = ").append(this.getSupervisor()).append("\n")
                .append("startedAt         = ").append(this.getStartedAt()).append("\n")
                .append("status            = ").append(this.getStatus())
                .toString();

    }

}
