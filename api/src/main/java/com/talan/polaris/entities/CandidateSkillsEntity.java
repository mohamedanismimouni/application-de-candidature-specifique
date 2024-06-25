package com.talan.polaris.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"candidate","createdAt","updatedAt","id"})

public class CandidateSkillsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "seq_candidateSkills_entity")
    @SequenceGenerator(name = "seq_candidateSkills_entity" , sequenceName = "seq_candidateSkills_entity", allocationSize = 1)
    @JsonProperty("id")
    Long id ;

    @CreatedDate
    @Column(nullable = true)
    @JsonProperty("createdAt")
    private Instant createdAt ;


    @LastModifiedDate
    @Column(nullable = true)
    @JsonProperty("updatedAt")
    private  Instant updatedAt ;

    @Column(name="skillName")
    private String skillName ;

    @JsonProperty("candidate")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name="id_candidate")
    private CandidateEntity candidateEntity;



}
