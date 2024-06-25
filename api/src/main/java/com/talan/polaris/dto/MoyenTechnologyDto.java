package com.talan.polaris.dto;

import lombok.Data;

@Data
public class MoyenTechnologyDto {
    public MoyenTechnologyDto(String name, double moyen) {
        this.name = name;
        this.moyen = moyen;
    }

    private String name;
    private double moyen;
}
