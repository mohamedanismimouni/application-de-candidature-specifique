package com.talan.polaris.mapper;

import com.talan.polaris.dto.RequiredSkillDTO;
import com.talan.polaris.entities.RequiredSkillEntity;

import org.modelmapper.ModelMapper;

public class RequiredSkillMapper {

    RequiredSkillMapper(){
    }

    public static RequiredSkillEntity convertRequiredSkillToEntity(RequiredSkillDTO requiredSkillDTO, ModelMapper modelMapper) {
        return modelMapper.map(requiredSkillDTO, RequiredSkillEntity.class);
    }


    public static RequiredSkillDTO convertRequiredSkillToDTO(RequiredSkillEntity requiredSkillEntity, ModelMapper modelMapper) {
        return modelMapper.map(requiredSkillEntity, RequiredSkillDTO.class);

    }



}
