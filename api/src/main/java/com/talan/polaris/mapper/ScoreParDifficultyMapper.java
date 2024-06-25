package com.talan.polaris.mapper;


import com.talan.polaris.dto.ScoreParDifficultyDto;
import com.talan.polaris.entities.ScoreParDifficultyEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface ScoreParDifficultyMapper {
    ScoreParDifficultyDto toScoreParDifficultyDto(ScoreParDifficultyEntity scoreParDifficulty);

    List<ScoreParDifficultyDto> toScoreParDifficultyDtos(List<ScoreParDifficultyEntity> scoreParDifficulties);

    ScoreParDifficultyEntity toScoreParDifficulty(ScoreParDifficultyDto scoreParDifficultyDto);
}
