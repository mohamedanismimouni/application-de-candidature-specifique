package com.talan.polaris.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

/**
 * ParametrageAppliEntity.
 *
 * @author Imen Mechergui
 * @since 1.1.0
 */
@Entity
@Table(name = "parametrageAppli")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ParametrageAppliEntity {


    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_parametrageAppli_entity")
    @SequenceGenerator(name = "seq_parametrageAppli_entity", sequenceName = "seq_parametrageAppli_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @CreatedDate
    @Column(nullable = false)
    private String createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
    private String parametre;
    private String typeParam;
    private String valeurParam;


}