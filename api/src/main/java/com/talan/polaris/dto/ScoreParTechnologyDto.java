package com.talan.polaris.dto;

import lombok.Data;


@Data
public class ScoreParTechnologyDto {
    private Long id;
    private double score;
    private TestDTO test;
    private PrmTechnologyDTO prmTechnology;
}
