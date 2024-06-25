package com.talan.polaris.repositories;

import com.talan.polaris.entities.ResponseTestEntity;
import com.talan.polaris.entities.ResultTestEntity;
import com.talan.polaris.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultTestRepository extends JpaRepository <ResultTestEntity,Long> {


    ResultTestEntity findResultByTest(TestEntity test);

    Optional<ResultTestEntity> findByTestAndResponse(TestEntity t, ResponseTestEntity r);
}
