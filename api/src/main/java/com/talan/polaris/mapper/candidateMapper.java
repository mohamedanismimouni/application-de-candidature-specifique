package com.talan.polaris.mapper;

import com.talan.polaris.dto.CandidateDTO;
import com.talan.polaris.entities.CandidateEntity;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@NoArgsConstructor
public class candidateMapper {

    public static CandidateEntity convertCandidateDTOToEntity (CandidateDTO candidateDTO , ModelMapper modelMapper){
        return modelMapper.map(candidateDTO , CandidateEntity.class);
    }

    public static CandidateDTO convertCandidateEntityToDTO(CandidateEntity candidateEntity , ModelMapper modelMapper){
        return  modelMapper.map(candidateEntity , CandidateDTO.class);
    }
}
