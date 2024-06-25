package com.talan.polaris.entities;

import com.talan.polaris.enumerations.RequestTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
/**
 * A mapped entity representing an open ended response domain model,
 * a subtype of the response domain model.
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
@Entity
@Table(name = "REQUEST_TYPE")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class RequestTypeEntity{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_request_type_entity")
    @SequenceGenerator(name = "seq_request_type_entity", sequenceName = "seq_request_type_entity", allocationSize = 1)
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
    private RequestTypeEnum label;

    @Column(nullable = true)
    private Long idEDM;

    @Column(nullable = true)
    private Boolean visibility;

    @Column(nullable = true)
    private Boolean isTemplateOfRequest;

    @Column(nullable = true)
    private Date uploadDate;

    @OneToMany(mappedBy="type")
    private Collection<DocumentRequestEntity> requests;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_signatory")
    private SignatoryEntity signatory;

    public RequestTypeEntity(Long id, Instant createdAt, Instant updatedAt, RequestTypeEnum label, Collection<DocumentRequestEntity> requests,
                             Boolean isTemplateOfRequest) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.label = label;
        this.requests = requests;
        this.isTemplateOfRequest =isTemplateOfRequest;
    }

    public RequestTypeEntity() {
    }
}
