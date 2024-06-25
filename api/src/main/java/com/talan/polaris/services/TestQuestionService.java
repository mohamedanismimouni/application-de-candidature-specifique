package com.talan.polaris.services;

import com.talan.polaris.entities.PrmTechnologyEntity;
import com.talan.polaris.entities.TestQuestionEntity;

import java.util.HashSet;
import java.util.List;

public interface TestQuestionService {
    List<TestQuestionEntity> findByTest (Long idTest);
    HashSet<PrmTechnologyEntity> findAllTechnologiesByTest(Long idTest);
}
