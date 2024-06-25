package com.talan.polaris.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequestDTO{
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String requestMotif;
    private String rejectionMotif;
    private RequestTypeDTO type;
    private RequestStatusDTO status;
    private CollaboratorDTO collaborator;
    private Long idEDM;
    private String reference;
    private Boolean createdByRH;
    private Boolean validateddBySystem;
    private Boolean withoutTemplate;
    private Instant  validatedAt;
    private CollaboratorDTO createdBy;
    private Long idEtiquetteEDM;
}
