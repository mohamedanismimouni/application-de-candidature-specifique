package com.talan.polaris.mapper;


import com.talan.polaris.dto.PrmDifficultyDTO;
import com.talan.polaris.entities.PrmDifficultyEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface PrmDifficultyMapper {

    PrmDifficultyDTO toPrmDifficultyDto(PrmDifficultyEntity prmDifficulty);

    List<PrmDifficultyDTO> toPrmDifficultyDtos(List<PrmDifficultyEntity> prmDifficulties);

    PrmDifficultyEntity toPrmDifficulty(PrmDifficultyDTO prmDifficultyDto);
}


