package com.talan.polaris.dto;

import com.talan.polaris.entities.QuestionTestEntity;
import com.talan.polaris.entities.ResponseTestEntity;
import com.talan.polaris.entities.TestEntity;
import lombok.Data;

import java.util.List;

@Data
public class TestResponsesDTO {
    TestEntity test;
    QuestionTestEntity question;
    List<ResponseTestEntity> responses;
    int index;
    Long testDuration;
    Boolean timeOver;
}
