package com.talan.polaris.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcquiredSkillDTO extends GenericDTO {

    @NotNull
    private CollaboratorDTO collaborator;

    @NotNull
    private SkillDTO skill;

    private List<AcquiredSkillLevelDTO> progress;


}
