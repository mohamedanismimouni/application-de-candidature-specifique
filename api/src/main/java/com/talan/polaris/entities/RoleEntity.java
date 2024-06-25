/*
package com.talan.polaris.entities;

import com.talan.polaris.enumerations.ProfileTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;

*/
/**
 * RoleEntity.
 *
 * @author Imen Mechergui
 * @since 1.0.1
 *//*

@Entity
@Table(name = "roles")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role_entity")
    @SequenceGenerator(name = "seq_role_entity", sequenceName = "seq_role_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
    @Enumerated(EnumType.STRING)
    @NaturalId
    private ProfileTypeEnum label;
    @Column(name = "abrv_role")
    private String abrevRole;
    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;
}*/
