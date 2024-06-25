package com.talan.polaris.dto;


import lombok.Data;

import java.util.Date;

@Data
public class TestDTO {

    private Long testid;
    private java.sql.Date creationDate;
    private Long testDuration;

    private Boolean done;
    private Boolean expired;
    private Long passageDuration;
    private Double totalScore;
    private CandidateDTO candidate;
    private Boolean send;
    private Date expirationDate;
}
