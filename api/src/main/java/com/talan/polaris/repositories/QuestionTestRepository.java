package com.talan.polaris.repositories;


import com.talan.polaris.entities.QuestionTestEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionTestRepository extends JpaRepository<QuestionTestEntity, Long> {
  /*  @Query(value="SELECT *  FROM  public.question where  prm_difficulty_id= ? and prm_technology_id=?  ORDER BY random() LIMIT ?",nativeQuery = true)
    List<QuestEntity> getAllQuestions(Long idPrmDifficulty,Long idPrmTechnology, Long nbQuestions);*/

    @Query(value="select q from QuestionTestEntity q where q.prmDifficulty.difficultyName= :diff and q.prmTechnology.technologyName= :tech")
    List<QuestionTestEntity> getQuestionsByDifficultyAndTechnology(@Param("diff")String diff, @Param("tech") String tech);

}
