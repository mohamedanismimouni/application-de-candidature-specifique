package com.talan.polaris.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;

/**
 * SignatoryEntity.
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
@Entity
@Table(name = "Signatories")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@ToString
public class SignatoryEntity {


    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_signataire_entity")
    @SequenceGenerator(name = "seq_signataire_entity", sequenceName = "seq_signataire_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    /**
     * firstName
     */
    private String firstName;


    /**
     * lastName
     */
    private String lastName;

    @OneToMany(mappedBy="signatory")
    private Collection<RequestTypeEntity> requests;
}