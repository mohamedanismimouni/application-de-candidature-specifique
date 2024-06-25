package com.talan.polaris.dto;

import com.talan.polaris.enumerations.SkillLevelEnum;
import com.talan.polaris.enumerations.SkillWeightEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RequiredSkillDTO   extends GenericDTO
{

    private SkillLevelEnum level;

    private SkillWeightEnum weight;

    private ProfileDTO profile;

    private SkillDTO skill;


}
