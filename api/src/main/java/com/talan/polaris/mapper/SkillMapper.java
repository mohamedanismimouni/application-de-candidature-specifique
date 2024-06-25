package com.talan.polaris.mapper;


import com.talan.polaris.dto.SkillDTO;
import com.talan.polaris.entities.SkillEntity;
import org.modelmapper.ModelMapper;

public class SkillMapper {

    SkillMapper(){
    }

    public static SkillEntity convertSkillToEntity(SkillDTO skillDTO, ModelMapper modelMapper) {
        return modelMapper.map(skillDTO, SkillEntity.class);
    }


    public static SkillDTO convertSkillToDTO(SkillEntity skillEntity, ModelMapper modelMapper) {
        return modelMapper.map(skillEntity, SkillDTO.class);

    }

}
