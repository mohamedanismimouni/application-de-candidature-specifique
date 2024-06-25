package com.talan.polaris.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


@Data
@Entity
@JsonIgnoreProperties(value = {"candidate"})
public class TestEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testid;
    private Date creationDate;
    private Long testDuration;
    private Boolean done;
    private Boolean expired;
    private Long passageDuration;
    private Double totalScore;
    private Boolean send;
    private Date expirationDate;

    @ManyToOne
    @JoinColumn
    @JsonProperty("candidate")
    private CandidateEntity candidate;

}
