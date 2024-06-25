package com.talan.polaris.services.impl;

import com.talan.polaris.entities.PrmTechnologyEntity;
import com.talan.polaris.entities.TestEntity;
import com.talan.polaris.entities.TestQuestionEntity;
import com.talan.polaris.mapper.TestQuestionMapper;
import com.talan.polaris.repositories.TestQuestionRepository;
import com.talan.polaris.repositories.TestRepository;
import com.talan.polaris.services.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class TestQuestionServiceImpl implements TestQuestionService {
    @Autowired
    private TestRepository testRepository;


    private TestQuestionMapper testQuestionMapper;

    @Autowired
    private TestQuestionRepository testQuestionRepository;


    @Override
    public List<TestQuestionEntity> findByTest(Long idTest) {
        Optional<TestEntity> test = testRepository.findById(idTest);
        if (test.isPresent()){
            return testQuestionRepository.findByTest(test.get());
        }else {
            return new ArrayList<>();
        }
    }

    @Override
    public HashSet<PrmTechnologyEntity> findAllTechnologiesByTest(Long idTest) {
        Optional<TestEntity> test = testRepository.findById(idTest);
        HashSet<PrmTechnologyEntity> technologies = new HashSet<>();
        if (test.isPresent()){
            List<TestQuestionEntity> testQuestions = testQuestionRepository.findByTest(test.get());
            testQuestions.forEach(question -> {
                if (technologies.contains(question.getQuestion().getPrmTechnology())){

                }else {
                    technologies.add(question.getQuestion().getPrmTechnology());
                }
            });
            return technologies;
        }else {
            return new HashSet<>();
        }
    }
}
