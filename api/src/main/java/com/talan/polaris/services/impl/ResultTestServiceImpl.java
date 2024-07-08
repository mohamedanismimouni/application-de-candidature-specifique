package com.talan.polaris.services.impl;

import com.talan.polaris.dto.TestResponsesDTO;
import com.talan.polaris.entities.*;
import com.talan.polaris.mapper.TestMapper;
import com.talan.polaris.repositories.ResponseTestRepository;
import com.talan.polaris.repositories.ResultTestRepository;
import com.talan.polaris.repositories.TestQuestionRepository;
import com.talan.polaris.repositories.TestRepository;
import com.talan.polaris.services.ResultTestService;
import com.talan.polaris.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ResultTestServiceImpl implements ResultTestService {

    @Autowired
    private ResultTestRepository resultTestRepository;

    private TestService testService;

    private TestMapper testMapper;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestQuestionRepository testQuestionRepository;

    @Autowired
    private ResponseTestRepository responseTestRepository;


    @Override
    public TestResponsesDTO saveResponses(TestResponsesDTO response) {
        int length = testService.findByTestId(response.getTest().getTestid()).size();
        Double score ;
        TestQuestionKeysEntity testQuestionKeys = new TestQuestionKeysEntity();
        testQuestionKeys.setQuestionid(response.getQuestion().getQuestionid());
        testQuestionKeys.setTestid(response.getTest().getTestid());
        TestQuestionEntity testQuestion = testQuestionRepository.findById(testQuestionKeys).get();
        List<ResponseTestEntity> response1 = response.getResponses();
        QuestionTestEntity question = response.getQuestion();
        response1.forEach(res -> {
            ResultTestEntity result = new ResultTestEntity();
            result.setQuestion(response.getQuestion());
            result.setTest(response.getTest());
            result.setResponse(res);
            resultTestRepository.save(result);
        });
        List<ResponseTestEntity> response2 = (List<ResponseTestEntity>) question.getResponses();
        testQuestion.setResult(compareResponses(response1,response2));
        testQuestionRepository.save(testQuestion);
        boolean l = length == response.getIndex();
        boolean to = response.getTimeOver();
        if(response.getIndex() >= 1 ){
            TestEntity tess = testRepository.findById(response.getTest().getTestid()).get();
            response.getTest().setCandidate(tess.getCandidate());
            response.getTest().setDone(true);
            response.getTest().setPassageDuration(response.getTestDuration());
            AtomicInteger counter = new AtomicInteger();
            testService.findByTestId(response.getTest().getTestid()).forEach(testQuestion1 ->{
                if(testQuestion1.getResult() == null){

                }else if (testQuestion1.getResult() == 1L ){
                    counter.getAndIncrement();

                }
            });
            score = Double.valueOf(counter.get()) * 100 / length ;
            response.getTest().setTotalScore(score);
            TestEntity test = testRepository.save(response.getTest());
        }
        if (l || to ){
            TestEntity tess = testRepository.findById(response.getTest().getTestid()).get();
            response.getTest().setCandidate(tess.getCandidate());
            AtomicInteger counter = new AtomicInteger();
            testService.findByTestId(response.getTest().getTestid()).forEach(testQuestion1 ->{
                if(testQuestion1.getResult() == null){

                }else if (testQuestion1.getResult() == 1L ){
                    counter.getAndIncrement();
                }
            });
            score = Double.valueOf(counter.get()) * 100 / length ;
            response.getTest().setTotalScore(score);
            response.getTest().setDone(true);
            response.getTest().setPassageDuration(response.getTestDuration());
            TestEntity test = testRepository.save(response.getTest());

        }
        return response;
    }
    private Long compareResponses(List<ResponseTestEntity> resp1, List<ResponseTestEntity> resp2) {
        Long resp;
        for (int i = resp2.size() - 1; i >= 0; i--) {
            if (resp2.get(i).getCorrect() == null || !resp2.get(i).getCorrect())
                resp2.remove(i);
        }
        if (resp2.size() != resp1.size())
            resp = 0L;
        else {
            resp = 1L;
            for (int i = 0; i < resp2.size(); i++) {
                if (!resp2.get(i).getId().equals(resp1.get(i).getId()))
                    resp = 0L;
            }
        }
        return resp;
    }
}
