package com.talan.polaris.dto;

import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerPositionDTO  {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    @NotNull
    private ProfileDTO profile;

    @NotNull
    private CollaboratorDTO collaborator;

    private CollaboratorDTO supervisor;

    private LocalDate startedAt;

    @NotNull
    private CareerPositionStatusEnum status;
}
