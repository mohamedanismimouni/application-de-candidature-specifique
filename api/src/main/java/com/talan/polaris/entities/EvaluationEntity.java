package com.talan.polaris.entities;

import com.talan.polaris.enumerations.EvaluationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
import java.time.LocalDate;

/**
 * A mapped entity representing the evaluation domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(
    name = "EVALUATIONS",
    uniqueConstraints = @UniqueConstraint(
        columnNames = { "evaluationDate", "CAREER_POSITION" }
    )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationEntity
        extends GenericEntity
        implements Comparable<EvaluationEntity> {

    private static final long serialVersionUID = 3791059333573240352L;

    @NotNull
    @Column(nullable = false)
    private LocalDate evaluationDate;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "CAREER_POSITION")
    private CareerPositionEntity careerPosition;

    @Column(nullable = true)
    private Integer supervisorRating;

    @Column(nullable = true)
    private String supervisorFeedback;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EvaluationStatusEnum status;
    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("EvaluationEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("evaluationDate    = ").append(this.getEvaluationDate()).append("\n")
                .append("careerPosition    = ").append(this.getCareerPosition()).append("\n")
                .append("rating            = ").append(this.getSupervisorRating()).append("\n")
                .append("feedback          = ").append(this.getSupervisorFeedback()).append("\n")
                .append("status            = ").append(this.getStatus())
                .toString();

    }

    @Override
    public int compareTo(EvaluationEntity o) {

        if (this.getEvaluationDate() != null && o.getEvaluationDate() != null)
            return o.getEvaluationDate().compareTo(this.getEvaluationDate());

        return 0;

    }

}
