package com.talan.polaris.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "USEFUL_DOCUMENT")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class UsefulDocumentEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_useful_document_entity")
    @SequenceGenerator(name = "seq_useful_document_entity", sequenceName = "seq_useful_document_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    private String label;

    @Column(nullable = true)
    private Long idEDM;

    @Column(nullable = true)
    private Date uploadDate;

    @Column(nullable = true)
    private byte[] thumbnail;


    public UsefulDocumentEntity(Long id, Instant createdAt, Instant updatedAt, String label, Long idEDM, Date uploadDate, byte[] thumbnail) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.label = label;
        this.idEDM = idEDM;
        this.uploadDate = uploadDate;
        this.thumbnail = thumbnail;
    }

    public UsefulDocumentEntity() {
    }
}
