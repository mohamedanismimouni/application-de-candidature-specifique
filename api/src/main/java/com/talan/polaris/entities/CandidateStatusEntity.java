package com.talan.polaris.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.talan.polaris.enumerations.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CANDIDATE_STATUS")
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(value = {"candidate","createdAt","updatedAt"})
public class CandidateStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_candidateStatus_entity")
    @SequenceGenerator(name = "seq_candidateStatus_entity", sequenceName = "seq_candidateStatus_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;


    @CreatedDate
    @Column(nullable = false)
    @JsonProperty("createdAt")
    private Instant createdAt;


    @LastModifiedDate
    @Column(nullable = false)
    @JsonProperty("updatedAt")
    private Instant updatedAt;


    @Enumerated(EnumType.STRING)
    @NaturalId
    private StatusEnum statusEnum;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateStatusEntity")
    @JsonProperty("candidate")
    private List<CandidateEntity> candidates = new ArrayList<>();


}
