package com.talan.polaris.dto;

import com.talan.polaris.entities.PrmTechnologyEntity;
import lombok.Data;

@Data
public class MoyTechCalcDto {

    public MoyTechCalcDto(PrmTechnologyEntity tech, Long nb, Long total) {
        this.tech = tech;
        this.nb = nb;
        this.total=total;
    }

    private PrmTechnologyEntity tech;
    private Long nb;
    private Long total;
}
