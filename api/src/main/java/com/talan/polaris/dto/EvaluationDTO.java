package com.talan.polaris.dto;

import java.time.LocalDate;

import com.talan.polaris.enumerations.EvaluationStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDTO extends GenericDTO {
    @NotNull
    private LocalDate evaluationDate;
    @NotNull
    private CareerPositionDTO careerPosition;
    private Integer supervisorRating;
    private String supervisorFeedback;
    @Enumerated(EnumType.STRING)
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


}
