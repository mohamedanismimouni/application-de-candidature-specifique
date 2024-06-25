package com.talan.polaris.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.talan.polaris.dto.CalendarRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "offer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"candidate"})
public class OfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_offer_entity")
    @SequenceGenerator(name = "seq_offer_entity", sequenceName = "seq_offer_entity" , allocationSize = 1)
    private Long id ;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;


    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @Column(name="reference")
    private String reference ;

    @Column(name = "subject")
    private String subject ;

    @Column(name = "contexte")
    private String contexte ;

    @Column(name = "description")
    private String description  ;

    @ManyToOne
    @JoinColumn(name = "department")
    private DepartmentEntity department ;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offerEntity")
    private List<offerRequirementEntity> offerRequirementEntities = new ArrayList<>();

    @JsonProperty("candidate")
    @ManyToMany(mappedBy = "offerEntities")
    private Collection<CandidateEntity> candidates;


    public static CalendarRequest.Builder ok() {
        return null;
    }
}
