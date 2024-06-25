package com.talan.polaris.repositories;

import com.talan.polaris.dto.DoneTestResultDto;
import com.talan.polaris.dto.MoyenTechnologyDto;
import com.talan.polaris.entities.CandidateEntity;
import com.talan.polaris.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepo extends JpaRepository<TestEntity,Long> {
    List<TestEntity> findByDoneAndTotalScoreBetween(Boolean done, Double value1, Double value2);
    List<TestEntity> findByDone(Boolean done);
    List<TestEntity> findBySendAndDone(Boolean send,Boolean done);
    List<TestEntity> findByCandidate(CandidateEntity candidate);



   }
