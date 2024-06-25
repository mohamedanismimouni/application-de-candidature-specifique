//package com.talan.polaris.entities;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.persistence.*;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//import java.util.List;
//
//@Entity
//@Table(name = "COLLABORATOR")
//@Getter
//@Setter
//@ToString
//@EntityListeners(AuditingEntityListener.class)
//public class CollabEntity {
//
//
//    /**
//     * serialVersionUID.
//     */
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_collab_entity")
//    @SequenceGenerator(name = "seq_collab_entity", sequenceName = "seq_collab_entity", allocationSize = 1)
//    @Column(name = "id")
//    private Long id;
//
//    @CreatedDate
//    @Column(nullable = false)
//    private Instant createdAt;
//
//    @LastModifiedDate
//    @Column(nullable = false)
//    private Instant updatedAt;
//
//    /** first name. */
//    @Column(name = "firstName")
//    private String firstName;
//
//    /** last name. */
//    @Column(name = "lastName")
//    private String lastName;
//
//    /** matricule. */
//    @Column(name = "matricule")
//    private String matricule;
//
//    /** Hiring Date :entry date. */
//    @Column(name = "entryDate")
//    @Temporal(TemporalType.DATE)
//    private Date entryDate;
//
//    /** Hiring Date :entry date. */
//    @Column(name = "endContractDate")
//    @Temporal(TemporalType.DATE)
//    private Date endContractDate;
//
//    /**  score. */
//    @Column(name = "score")
//    private Integer score;
//
//
//    /** qualification. */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_qualification")
//    private QualificationEntity qualification;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_function")
//    private FunctionEntity function;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_civilite")
//    private CivilityEntity civility;
//
//    /** id byblos user */
//    @Column(name = "id_byblos")
//    private Long idByblos;
//
//    @OneToOne(mappedBy = "collab")
//    private UserEntity user;
//
//
//    @OneToMany(mappedBy="collaborator")
//    private Collection<SalaryHistoryEntity> histories;
//    
//    
//     @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//	 @JoinColumn(name = "idCollab")
//	 private List<MoodCollab> moodCollab = new ArrayList<>();
//
//}
