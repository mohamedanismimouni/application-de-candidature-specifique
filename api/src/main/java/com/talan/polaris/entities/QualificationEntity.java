package com.talan.polaris.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;

@Entity
@Table(name = "qualification")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class QualificationEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_qualification_entity")
    @SequenceGenerator(name = "seq_qualification_entity", sequenceName = "seq_qualification_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    /**
     * l_qualif.
     */
    @Column(name = "l_qualif")
    private String libelle;

    /**
     * sous Qualification.
     */
    @Column(name = "sousQualification")
    private String sousQualification;

    @OneToMany(mappedBy="qualification")
    private Collection<CollaboratorEntity> collabs;

}