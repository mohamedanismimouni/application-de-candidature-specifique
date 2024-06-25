package com.talan.polaris.mapper;

import com.talan.polaris.dto.TestDTO;
import com.talan.polaris.entities.TestEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface TestMapper {

    TestDTO toTestDto(TestEntity test);

    List<TestDTO> toTestDtos(List<TestEntity> tests);

    TestEntity toTest(TestDTO testDto);
}


