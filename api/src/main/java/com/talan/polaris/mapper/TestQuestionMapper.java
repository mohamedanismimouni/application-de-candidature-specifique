package com.talan.polaris.mapper;


import com.talan.polaris.dto.TestQuestionDTO;
import com.talan.polaris.entities.TestQuestionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface TestQuestionMapper {

    TestQuestionDTO toTestQuestionDto(TestQuestionEntity testQuestion);

    List<TestQuestionDTO> toTestQuestionDtos(List<TestQuestionEntity> testsQuestions);

    TestQuestionEntity toTestQuestion(TestQuestionDTO testQuestionDto);
}
