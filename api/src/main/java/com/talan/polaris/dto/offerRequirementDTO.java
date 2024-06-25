package com.talan.polaris.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class offerRequirementDTO {

    private Long id ;
    private Instant createdAt;
    private Instant updatedAt;
    private String requirementName ;
    private offerDTO offer ;

}
