package com.talan.polaris.services;

import com.talan.polaris.dto.ScorePerDifficultyDto;
import com.talan.polaris.entities.ScoreParDifficultyEntity;
import com.talan.polaris.entities.TestEntity;

import java.util.List;

public interface ScoreParDifficultyService {
    List<ScorePerDifficultyDto> findByTest(TestEntity test);

}
