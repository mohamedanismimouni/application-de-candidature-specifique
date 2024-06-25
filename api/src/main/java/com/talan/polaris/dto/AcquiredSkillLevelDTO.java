package com.talan.polaris.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.talan.polaris.enumerations.SkillLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcquiredSkillLevelDTO  extends GenericDTO implements Comparable<AcquiredSkillLevelDTO> {

    @NotNull
    private SkillLevelEnum level;

    @JsonIgnore
    private AcquiredSkillDTO acquiredSkill;


    @Override
    public int compareTo(AcquiredSkillLevelDTO o) {

        if (this.getCreatedAt() != null && o.getCreatedAt() != null)
            return this.getCreatedAt().compareTo(o.getCreatedAt());

        return 0;

    }
}
