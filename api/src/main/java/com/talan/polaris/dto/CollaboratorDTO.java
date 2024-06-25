package com.talan.polaris.dto;


import com.talan.polaris.enumerations.AccountStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollaboratorDTO {

    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String email;
    private AccountStatusEnum accountStatus;
    private String firstName;
    private String lastName;
    //private String profileType;
    private LocalDate recruitedAt;
    private String secretWord;
    private boolean passedOnboardingProcess;
    private String matricule;
    private QualificationDTO qualification;
    private FunctionDTO function;
    private Date entryDate;
    private Date endContractDate;
    private CivilityDTO civility;
    private Long idByblos;
    private Integer score;



}
