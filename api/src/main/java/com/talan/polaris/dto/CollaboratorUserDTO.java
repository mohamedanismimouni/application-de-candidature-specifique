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
public class CollaboratorUserDTO {

    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String firstName;
    private String lastName;
    private String matricule;
    private QualificationDTO qualification;
    private FunctionDTO function;
    private Date entryDate;
    private Date endContractDate;
    private CivilityDTO civility;
    //private UserDTO user;
    private String email;
    private AccountStatusEnum accountStatus;
    private LocalDate recruitedAt;
    private String secretWord;
    private boolean passedOnboardingProcess;
    private Integer score;
}
