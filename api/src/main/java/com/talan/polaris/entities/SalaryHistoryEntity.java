package com.talan.polaris.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

/**
 * SalaryHistoryEntity.
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
@Entity
@Table(name = "SalaryHistories")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@ToString
public class SalaryHistoryEntity {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_historySal_entity")
    @SequenceGenerator(name = "seq_historySal_entity", sequenceName = "seq_historySal_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @Temporal(TemporalType.DATE)
    private Date payRollDate;

   private Double baseSalary;

   private Double netSalary;

   private Double bonus;

    private Double reminder;

    private Instant salaryUpdatedAt;

    private Boolean updatedByBO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_collaborator")
    private CollaboratorEntity collaborator;

    //encrypted value
    private String encryptedBaseSalary;

    private String  encryptedNetSalary;

    private String encryptedBonus;

    private String encryptedReminder;


}