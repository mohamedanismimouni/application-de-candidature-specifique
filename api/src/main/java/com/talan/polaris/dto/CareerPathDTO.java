package com.talan.polaris.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerPathDTO extends GenericDTO {

    @NotNull
    private int yearsOfExperience;

    @NotNull
    private CareerStepDTO fromCareerStep;

    @NotNull
    private CareerStepDTO toCareerStep;


}
