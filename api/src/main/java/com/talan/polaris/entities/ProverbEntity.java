package com.talan.polaris.entities;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.Instant;
/**
 * A mapped entity representing an open ended response domain model,
 * a subtype of the response domain model.
 *
 * @author Imen Mechergui
 * @since 2.0.0
 */
@Entity
@Table(name = "PROVERB")
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class ProverbEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_proverb_entity")
    @SequenceGenerator(name = "seq_proverb_entity", sequenceName = "seq_proverb_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    private String text;

    private Boolean bySystem;

    private String author;

    private Boolean published;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="createdBy_id", nullable=true)
    private CollaboratorEntity creator;}
