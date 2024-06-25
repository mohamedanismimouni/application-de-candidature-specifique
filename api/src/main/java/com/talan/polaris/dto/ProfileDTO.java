package com.talan.polaris.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO extends GenericDTO {

	private String label;

	private TeamDTO team;

	private CareerStepDTO careerStep;

}
