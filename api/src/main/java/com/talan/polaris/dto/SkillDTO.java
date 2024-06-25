package com.talan.polaris.dto;

import com.talan.polaris.enumerations.SkillTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkillDTO extends GenericDTO {

	private SkillTypeEnum skillType;
	private String label;
}
