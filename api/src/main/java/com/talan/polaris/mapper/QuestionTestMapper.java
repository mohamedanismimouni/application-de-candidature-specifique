package com.talan.polaris.mapper;


import com.talan.polaris.dto.QuestionTestDTO;
import com.talan.polaris.entities.QuestionTestEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface QuestionTestMapper {

	QuestionTestDTO toQuestionDto(QuestionTestEntity question);
	List<QuestionTestDTO> toQuestionDtos(List<QuestionTestEntity> prmTechnologies);

	QuestionTestEntity toQuestion(QuestionTestDTO questionDto);
}
