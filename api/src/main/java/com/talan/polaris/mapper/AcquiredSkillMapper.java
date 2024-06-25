package com.talan.polaris.mapper;

import com.talan.polaris.dto.AcquiredSkillDTO;
import com.talan.polaris.entities.AcquiredSkillEntity;
import org.modelmapper.ModelMapper;

public class AcquiredSkillMapper {

     AcquiredSkillMapper() {
    }

    public static AcquiredSkillEntity convertRequiredSkillToEntity(AcquiredSkillDTO acquiredSkillDTO, ModelMapper modelMapper) {
        return modelMapper.map(acquiredSkillDTO, AcquiredSkillEntity.class);
    }

    public static AcquiredSkillDTO convertRequiredSkillToDTO(AcquiredSkillEntity acquiredSkillEntity, ModelMapper modelMapper) {
        return modelMapper.map(acquiredSkillEntity, AcquiredSkillDTO.class);

    }
}
