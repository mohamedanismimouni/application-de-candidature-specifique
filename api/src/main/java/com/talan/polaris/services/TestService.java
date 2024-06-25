package com.talan.polaris.services;


import com.talan.polaris.dto.ResultTestQuestionDTO;
import com.talan.polaris.dto.TestDTO;
import com.talan.polaris.entities.CandidateEntity;
import com.talan.polaris.entities.TestEntity;
import com.talan.polaris.entities.TestQuestionEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface TestService {
   void updateExpiration();
   Optional<TestEntity> findById(Long id) ;
   List<TestDTO> findAll();
   List<TestQuestionEntity> findByTestId(Long id);
   boolean sendTest (HashSet<String> technologies , Long idCandidate);
   List<ResultTestQuestionDTO> getResultDetail(Long idtest);
   CandidateEntity getCandidateByTestId(Long id);
   TestEntity getTestByCandidate (Long idCandidate);
}
