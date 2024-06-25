package com.talan.polaris.dto;


import com.talan.polaris.entities.CandidateStatusEntity;
import com.talan.polaris.entities.OfferEntity;
import com.talan.polaris.enumerations.CandidacyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDTO {
    private Long id ;
    private String firstName ;
    private  String lastName ;
    private Long phoneNumber ;
    private String email;
    private String id_cv;
    private CandidateStatusEntity candidateStatusEntity;
    private Collection<CandidateSkillsDTO> candidateSkills ;
    private Collection<OfferEntity> offerEntities;
    private CandidacyTypeEnum candidacyType;
    private Date dateNaissance;
    private String candidateImg;
    private String emailSecondaire;
    private String posteActuel;
    private String societeActuelle;
    private String universite;
}
