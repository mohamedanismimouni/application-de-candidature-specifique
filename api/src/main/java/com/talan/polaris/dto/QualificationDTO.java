package com.talan.polaris.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualificationDTO {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String libelle;
    private String sousQualification;
}
