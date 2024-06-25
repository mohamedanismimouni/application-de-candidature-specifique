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
@Table(name = "function")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class FunctionEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_function_entity")
    @SequenceGenerator(name = "seq_function_entity", sequenceName = "seq_function_entity", allocationSize = 1)
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
    @Column(name = "l_function")
    private String libelle;

    @OneToMany(mappedBy="function")
    private Collection<CollaboratorEntity> collabs;
}
