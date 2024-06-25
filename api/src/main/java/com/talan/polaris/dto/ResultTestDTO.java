package com.talan.polaris.dto;

import lombok.Data;

@Data
public class ResultTestDTO {

    private Long id;
    private TestDTO test;
    private ResponseTestDTO response;
    private QuestionTestDTO question;
}
