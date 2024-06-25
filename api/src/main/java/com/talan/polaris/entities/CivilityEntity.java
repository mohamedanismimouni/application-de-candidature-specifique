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
@Table(name = "Civility")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class CivilityEntity {


    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_civility_entity")
    @SequenceGenerator(name = "seq_civility_entity", sequenceName = "seq_civility_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    /**
     * l_civilite.
     */
    @Column(name = "l_civilite")
    private String label;


    /**
     * abrv_civ.
     */
    @Column(name = "abrv_civ")
    private String abrev;

    @OneToMany(mappedBy="civility")
    private Collection<CollaboratorEntity> collabs;
}