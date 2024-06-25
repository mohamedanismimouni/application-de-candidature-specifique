package com.talan.polaris.repositories;


import com.talan.polaris.dto.MoyTechCalcDto;
import com.talan.polaris.entities.ScoreParTechnology;
import com.talan.polaris.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreParTechnologyRepo extends JpaRepository<ScoreParTechnology,Long> {
    List<ScoreParTechnology> findByTest(TestEntity test);

}
