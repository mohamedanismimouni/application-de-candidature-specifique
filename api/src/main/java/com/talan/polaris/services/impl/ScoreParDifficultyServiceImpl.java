package com.talan.polaris.services.impl;

import com.talan.polaris.dto.MoyDeffCalcDto;
import com.talan.polaris.dto.ScoreParDifficultyDto;
import com.talan.polaris.dto.ScorePerDifficultyDto;
import com.talan.polaris.entities.ScoreParDifficultyEntity;
import com.talan.polaris.entities.TestEntity;
import com.talan.polaris.mapper.ScoreParDifficultyMapper;
import com.talan.polaris.repositories.ScoreParDifficultyRepo;
import com.talan.polaris.repositories.TestQuestionRepo;
import com.talan.polaris.repositories.TestQuestionRepository;
import com.talan.polaris.repositories.TestRepo;
import com.talan.polaris.services.ScoreParDifficultyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ScoreParDifficultyServiceImpl implements ScoreParDifficultyService {
    @Autowired
    ScoreParDifficultyRepo scoreParDifficultyRepo;

    ScoreParDifficultyMapper scoreParDifficultyMapper;
    @Autowired
    TestQuestionRepository testQuestionRepo;
    @Autowired
    TestRepo testRepo;

    @Override
    public List<ScorePerDifficultyDto> findByTest(TestEntity test){
        List <ScoreParDifficultyDto> liste=scoreParDifficultyMapper.toScoreParDifficultyDtos(scoreParDifficultyRepo.findByTest(test));
        List<ScorePerDifficultyDto> listee=new ArrayList<>();
        for(int i=0;i<liste.size();i++){
            ScorePerDifficultyDto scorePerDifficultyDto=new ScorePerDifficultyDto();
            scorePerDifficultyDto.setDifficultyName(liste.get(i).getPrmDifficulty().getDifficultyName());
            scorePerDifficultyDto.setScoreDifficulty(liste.get(i).getScore());
            listee.add(scorePerDifficultyDto);
        }
        return listee;
    }

}
