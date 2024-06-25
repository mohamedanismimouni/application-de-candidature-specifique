package com.talan.polaris.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "JOBHISTORY")
@Getter
@Setter
public class JobHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_jobHistory_entity")
    @SequenceGenerator(name = "seq_jobHistory_entity", sequenceName = "seq_jobHistory_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(nullable = true)
    private Instant startDate;

    @Column(nullable = true)
    private Instant endDate;

    @Column(nullable = true)
    private long jobDuration;

    @Column(nullable = true)
    private String jobName;

    @Column(nullable = true)
    private String jobStatus;

}
