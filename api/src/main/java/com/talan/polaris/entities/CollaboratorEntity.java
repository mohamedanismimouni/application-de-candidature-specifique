package com.talan.polaris.entities;

import com.talan.polaris.enumerations.AccountStatusEnum;
import com.talan.polaris.utils.validation.constraints.EmailConstraint;
import com.talan.polaris.utils.validation.groups.CreateValidationGroup;
import com.talan.polaris.utils.validation.groups.UpdateValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "COLLABORATOR")
@Getter
@Setter
@ToString
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CollaboratorEntity {


    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_collab_entity")
    @SequenceGenerator(name = "seq_collab_entity", sequenceName = "seq_collab_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    /** first name. */
    @Column(name = "firstName")
    private String firstName;

    /** last name. */
    @Column(name = "lastName")
    private String lastName;

    /** matricule. */
    @Column(name = "matricule")
    private String matricule;

    /** Hiring Date :entry date. */
    @Column(name = "entryDate")
    @Temporal(TemporalType.DATE)
    private Date entryDate;

    /** Hiring Date :entry date. */
    @Column(name = "endContractDate")
    @Temporal(TemporalType.DATE)
    private Date endContractDate;

    /**  score. */
    @Column(name = "score")
    private Integer score;
    
    /** qualification. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_qualification")
    private QualificationEntity qualification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_function")
    private FunctionEntity function;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_civilite")
    private CivilityEntity civility;

    /** id byblos user */
    @Column(name = "id_byblos")
    private Long idByblos;

    @OneToMany(mappedBy="collaborator")
    private Collection<SalaryHistoryEntity> histories;

    //userEntity
    @NotNull(groups = { CreateValidationGroup.class, UpdateValidationGroup.class })
    @EmailConstraint(groups = { CreateValidationGroup.class, UpdateValidationGroup.class })
    @Column(unique = true)
    private String email;

    @NotNull(groups = { UpdateValidationGroup.class })
    @Enumerated(EnumType.STRING)
    private AccountStatusEnum accountStatus;

    @OneToMany(mappedBy="createdBy")
    private Collection<DocumentRequestEntity> documentRequests;

    @NotNull(groups = { CreateValidationGroup.class, UpdateValidationGroup.class })
    @Column(nullable = true)
    private LocalDate recruitedAt;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "MEMBER_OF")
    private TeamEntity memberOf;

    @Column(nullable = true)
    private String secretWord;

    @Column(nullable = true)
    private boolean passedOnboardingProcess;

    @OneToMany(mappedBy="collaborator")
    private Collection<DocumentRequestEntity> requests;

    @OneToOne(optional = true, fetch = FetchType.LAZY, mappedBy = "managedBy")
    private TeamEntity managerOf;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_profile_type",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "profile_type_id", referencedColumnName = "id"))
    private Collection<ProfileTypeEntity> profileTypeEntity;
  

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
 	 @JoinColumn(name = "idCollab")
 	 private List<MoodCollab> moodCollab = new ArrayList<>();

    @OneToMany(mappedBy="creator")
    private Collection<ProverbEntity> proverbs;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "collaborator_event",
            joinColumns = @JoinColumn(name = "collab_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
    private Collection<EventEntity> events;

    @OneToOne(mappedBy = "collaborator")
    private EntretienEntity entretien;

    public CollaboratorEntity() {

    }


}
