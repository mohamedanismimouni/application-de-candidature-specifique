package com.talan.polaris.dto;


import lombok.Data;


@Data
public class ScoreParDifficultyDto {
    private Long id;
    private double score;
    private TestDTO test;
    private PrmDifficultyDTO prmDifficulty;
}
