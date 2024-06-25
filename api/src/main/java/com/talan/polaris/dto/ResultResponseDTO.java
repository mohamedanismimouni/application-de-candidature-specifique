package com.talan.polaris.dto;

import lombok.Data;

@Data
public class ResultResponseDTO {
    private Long id;
    private String value;
    private Boolean correct;
    private Boolean checked;
}
