package com.talan.polaris.dto;

import com.talan.polaris.entities.CollaboratorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OnboardingDTO  extends GenericDTO {

    @NotBlank
    private String secretWordPart;

    private Integer rating;

    @NotNull
    private CollaboratorDTO secretWordPartHolder;

    @NotNull
    private CollaboratorDTO freshRecruit;
}
