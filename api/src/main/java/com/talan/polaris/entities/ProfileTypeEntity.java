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

@Entity
@Table(name = "profile_type")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ProfileTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_profile_type_entity")
    @SequenceGenerator(name = "seq_profile_type_entity", sequenceName = "seq_profile_type_entity", allocationSize = 1)
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
    @ManyToMany(mappedBy = "profileTypeEntity")
    private Collection<CollaboratorEntity> users;
}
