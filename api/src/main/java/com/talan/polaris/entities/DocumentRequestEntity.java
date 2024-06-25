package com.talan.polaris.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "document_requests")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class DocumentRequestEntity{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_document_request_entity")
    @SequenceGenerator(name = "seq_document_request_entity", sequenceName = "seq_document_request_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = true)
    private String requestMotif;

    @Column(nullable = true)
    private String rejectionMotif;

    @Column(nullable = true)
    private String reference;

    @Column(nullable = true)
    private Long idEDM;

    @Column(nullable = true)
    private Long idEtiquetteEDM;

    @Column(nullable = true)
    private Boolean createdByRH;

    @Column(nullable = true)
    private Boolean withoutTemplate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="collaborator_request_id", nullable=false)
    private CollaboratorEntity  collaborator;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="status_id", nullable=false)
    private RequestStatusEntity status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="type_id", nullable=false)
    private RequestTypeEntity type;

    @Column(nullable = true)
    private Boolean validateddBySystem;

    @Column(nullable = true)
    private Instant  validatedAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="createdBy_id", nullable=true)
    private CollaboratorEntity createdBy;


    public DocumentRequestEntity(Long id, Instant createdAt, Instant updatedAt, String requestMotif, String rejectionMotif, CollaboratorEntity  collaborator, RequestTypeEntity type, RequestStatusEntity status, CollaboratorEntity createdBy) {

        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.requestMotif = requestMotif;
        this.rejectionMotif = rejectionMotif;
        this.collaborator = collaborator;
        this.type = type;
        this.status = status;
        this.createdBy = createdBy;
    }

    public DocumentRequestEntity() {
    }
}
