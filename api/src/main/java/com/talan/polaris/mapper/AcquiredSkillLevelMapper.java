package com.talan.polaris.mapper;

import com.talan.polaris.dto.AcquiredSkillLevelDTO;
import com.talan.polaris.entities.AcquiredSkillLevelEntity;
import org.modelmapper.ModelMapper;

public class AcquiredSkillLevelMapper {

     AcquiredSkillLevelMapper() {
     }

    public static AcquiredSkillLevelEntity convertRequiredSkillToEntity(AcquiredSkillLevelDTO acquiredSkillLevelDTO, ModelMapper modelMapper) {
        return modelMapper.map(acquiredSkillLevelDTO, AcquiredSkillLevelEntity.class);
    }


    public static AcquiredSkillLevelDTO convertRequiredSkillToDTO(AcquiredSkillLevelEntity acquiredSkillLevelEntity, ModelMapper modelMapper) {
        return modelMapper.map(acquiredSkillLevelEntity, AcquiredSkillLevelDTO.class);

    }
}
