package com.talan.polaris.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"candidate","id","createdAt","updatedAt"})
public class CandidateSkillsDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("createdAt")
    private Instant createdAt ;
    @JsonProperty("updatedAt")
    private Instant updatedAt;
    private String skillName;
    @JsonProperty("candidate")
    private CandidateDTO candidateEntity;
}
