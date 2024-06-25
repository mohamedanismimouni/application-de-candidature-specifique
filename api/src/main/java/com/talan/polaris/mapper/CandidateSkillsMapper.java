package com.talan.polaris.mapper;

import com.talan.polaris.dto.CandidateDTO;
import com.talan.polaris.dto.CandidateSkillsDTO;
import com.talan.polaris.entities.CandidateEntity;
import com.talan.polaris.entities.CandidateSkillsEntity;
import org.modelmapper.ModelMapper;

public class CandidateSkillsMapper {
    public static CandidateSkillsEntity convertCandidateSkillsDTOToEntity (CandidateSkillsDTO candidateSkillsDTO , ModelMapper modelMapper){
        return modelMapper.map(candidateSkillsDTO , CandidateSkillsEntity.class);
    }

    public static CandidateSkillsDTO convertCandidateSkillsEntityToDTO(CandidateSkillsEntity candidateSkillsEntity , ModelMapper modelMapper){
        return  modelMapper.map(candidateSkillsEntity , CandidateSkillsDTO.class);
    }
}
