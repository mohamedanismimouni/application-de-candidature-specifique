package com.talan.polaris.repositories;


import com.talan.polaris.entities.QuestionTestEntity;
import com.talan.polaris.entities.ResponseTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ResponseTestRepository extends JpaRepository<ResponseTestEntity, Long> {

    @Query(value = "SELECT * FROM ResponseTestEntity WHERE question_questionid = ?", nativeQuery = true)
    List<ResponseTestEntity> getResponses(Long id);

    List<ResponseTestEntity> findByQuestion(QuestionTestEntity question);
    
}
