package com.talan.polaris.entities;


import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;



/**
 * HumeurCollabEntity.
 * SalaryHistoryEntity.
 *
 * @author Nadhir Fallah
 * @since 1.0.0
 */

@Entity
@Table(name = "Config_Score")
@Getter
@Setter
public class ConfigScore {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_score_entity")
    @SequenceGenerator(name = "seq_score_entity", sequenceName = "seq_score_entity", allocationSize = 1)
    @Column(name = "id_component")
    private Long idComponent;

    @Column(name = "Component_Label", nullable = false)
    private String componentLabel;

    @Column(nullable = true)
    private Integer componentScore;
}

