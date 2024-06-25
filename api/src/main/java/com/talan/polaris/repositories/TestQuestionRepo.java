package com.talan.polaris.repositories;

import com.talan.polaris.entities.QuestionTestEntity;
import com.talan.polaris.entities.TestEntity;
import com.talan.polaris.entities.TestQuestionEntity;
import com.talan.polaris.entities.TestQuestionKeysEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestQuestionRepo extends JpaRepository<TestQuestionEntity, TestQuestionKeysEntity> {
	List<TestQuestionEntity> findByTest(TestEntity t);
    TestQuestionEntity findByQuestion(QuestionTestEntity question);
	List<Optional<TestQuestionEntity>> findByTest(Optional<TestEntity> findById);



}
