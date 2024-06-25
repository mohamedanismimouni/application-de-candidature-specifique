package com.talan.polaris.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class ScoreParDifficultyEntity implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double score;

    @ManyToOne
    @JoinColumn
    private TestEntity test;

    @ManyToOne
    @JoinColumn
    private PrmDifficultyEntity prmDifficulty;
}
