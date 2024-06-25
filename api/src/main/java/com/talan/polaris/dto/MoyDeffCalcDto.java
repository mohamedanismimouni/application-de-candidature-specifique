package com.talan.polaris.dto;

import com.talan.polaris.entities.PrmDifficultyEntity;
import lombok.Data;

@Data
public class MoyDeffCalcDto {
    private PrmDifficultyEntity difficulty;
    private Long nb;
    private Long total;

    public MoyDeffCalcDto(PrmDifficultyEntity difficulty, Long nb, Long total) {
        this.difficulty = difficulty;
        this.nb = nb;
        this.total=total;
    }
}
