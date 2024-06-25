package com.talan.polaris.dto;


import lombok.Data;

@Data
public class ResponseTestDTO {

    private Long id;

    private String value;
    private Boolean correct;
    private QuestionTestDTO question;

}
