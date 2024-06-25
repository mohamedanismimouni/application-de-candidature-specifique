package com.talan.polaris.entities;

import com.talan.polaris.enumerations.CandidacyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "CANDIDATE")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "seq_candidate_entity")
    @SequenceGenerator(name = "seq_candidate_entity",sequenceName = "seq_candidate_entity" , allocationSize = 1)
    private Long id ;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt ;

    @LastModifiedDate
    @Column(nullable = false)
    private  Instant updatedAt ;

    @Column(name = "firstName")
    private String firstName ;

    @Column (name = "lastName")
    private  String lastName ;

    @Column(name ="phoneNumber")
    private Long phoneNumber ;

    @Column(name ="email")
    private String email;

    @Column(name = "id_cv")
    private String id_cv;

    @Enumerated(EnumType.STRING)
    private CandidacyTypeEnum candidacyType;


   @Column(columnDefinition="text", length=10485760)
    private String candidateImg;


    @Column(name = "dateNaissance")
    private Date dateNaissance;

    @Column(name = "emailSecondaire")
    private String emailSecondaire;

    @Column(name = "posteActuel")
    private String posteActuel;

    @Column(name = "societeActuelle")
    private String societeActuelle;

    @Column(name = "universite")
    private String universite;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CANDIDATE_OFFERS",
               joinColumns = @JoinColumn(name = "CANDIDATE_ID",
                       referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "OFFER_ID",
                        referencedColumnName = "id"))
    private Collection<OfferEntity>  offerEntities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateEntity")
    private Collection<CandidateSkillsEntity> candidateSkills ;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn( name="CANDIDATE_STATUS")
    private CandidateStatusEntity candidateStatusEntity;

    @OneToOne(mappedBy = "candidate")
    private EntretienEntity entretien;

}
