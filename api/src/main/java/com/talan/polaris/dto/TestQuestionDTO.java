package com.talan.polaris.dto;


import com.talan.polaris.entities.TestQuestionKeysEntity;
import lombok.Data;

@Data
public class TestQuestionDTO {

    private TestQuestionKeysEntity id;
    private Long result;
    private TestDTO test;
    private QuestionTestDTO question;
}
