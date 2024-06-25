package com.talan.polaris.mapper;

import com.talan.polaris.dto.CareerStepDTO;
import com.talan.polaris.entities.CareerStepEntity;
import org.modelmapper.ModelMapper;


public class CareerStepMapper {

    CareerStepMapper() {
    }

    public static CareerStepDTO convertCareerStepToDTO(CareerStepEntity careerStepEntity , ModelMapper mapper) {
        return mapper.map(careerStepEntity,CareerStepDTO.class);
    }

    public static CareerStepEntity convertCareerStepToEntity(CareerStepDTO careerStepDTO, ModelMapper mapper) {
        return mapper.map(careerStepDTO,CareerStepEntity.class);
    }

}
