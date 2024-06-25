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
@Table(name = "offerRequirement")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"offer"})
public class offerRequirementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_offerRequirement_entity")
    @SequenceGenerator(name = "seq_offerRequirement_entity", sequenceName = "seq_offerRequirement_entity" , allocationSize = 1)
    private Long id ;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;


    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @Column(name = "requirementName")
    private String requirementName ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false , name = "id_offer")
    @JsonProperty("offer")
    private OfferEntity offerEntity ;
}
