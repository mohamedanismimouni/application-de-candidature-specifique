package com.talan.polaris.repositories;

import com.talan.polaris.dto.MoyDeffCalcDto;
import com.talan.polaris.entities.ScoreParDifficultyEntity;
import com.talan.polaris.entities.TestEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreParDifficultyRepo extends JpaRepository<ScoreParDifficultyEntity, Long> {

	List<ScoreParDifficultyEntity> findByTest(TestEntity test);


}
