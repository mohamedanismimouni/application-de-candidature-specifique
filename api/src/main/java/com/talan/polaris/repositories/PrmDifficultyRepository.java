package com.talan.polaris.repositories;


import com.talan.polaris.entities.PrmDifficultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrmDifficultyRepository extends JpaRepository<PrmDifficultyEntity, Long> {
    PrmDifficultyEntity findPrmDifficultyByDifficultyName(String difficultyName);

}
