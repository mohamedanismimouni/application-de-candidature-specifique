package com.talan.polaris.mapper;

import com.talan.polaris.dto.CareerPathDTO;
import com.talan.polaris.entities.CareerPathEntity;
import org.modelmapper.ModelMapper;

public class CarrerPathMapper {

    CarrerPathMapper() {
    }

    public static CareerPathDTO convertCareerPathToDTO(CareerPathEntity careerPathEntity , ModelMapper mapper ) {
        return mapper.map(careerPathEntity,CareerPathDTO.class);
    }


    public static CareerPathEntity convertCareerPathToEntity(CareerPathDTO careerPathDTO , ModelMapper mapper) {
        return mapper.map(careerPathDTO,CareerPathEntity.class);
    }


}
