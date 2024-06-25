package com.talan.polaris.entities;

import com.talan.polaris.enumerations.RequestStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;

@Entity
@Table(name = "REQUEST_STATUS")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class RequestStatusEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_request_status_entity")
    @SequenceGenerator(name = "seq_request_status_entity", sequenceName = "seq_request_status_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatusEnum label;

    @OneToMany(mappedBy="status")
    private Collection<DocumentRequestEntity> requests;

    public RequestStatusEntity(Long id, Instant createdAt, Instant updatedAt, RequestStatusEnum label, Collection<DocumentRequestEntity> requests) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.label = label;
        this.requests = requests;
    }

    public RequestStatusEntity() {
    }
}
