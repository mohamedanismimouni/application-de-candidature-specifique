package com.talan.polaris.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResultTestQuestionDTO {
    private Long id;
    private String statement;
    private String technologyname;
    private String code;
    private Boolean result;
    private List<ResultResponseDTO> resultResponseDtoList;
    private Double scoreTotal;
    private String candidateName;
    private String candidateEmail;
    private String candidatePhone;
}
