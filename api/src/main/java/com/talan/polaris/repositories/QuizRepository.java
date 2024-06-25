package com.talan.polaris.repositories;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.talan.polaris.entities.QuizEntity;
 
@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, String> {
	
	
	@Query("select DISTINCT(q) from QuizEntity q LEFT join q.answeredBy u where (select count(*) from q.answeredBy u where u.email = :email  ) = 0 ")
	Collection<QuizEntity> findUnansweredQuiz(@Param("email") String email);
	
	@Query("select q from QuizEntity q  where q.id = :id ")
	QuizEntity findQuizById(@Param("id") String id);
	
	@Query("select COUNT(q) from QuizEntity q LEFT join q.answeredBy u where (select count(*) from q.answeredBy u where u.email = :email ) != 0 ")
	Integer CountAnsweredQuiz(@Param("email") String email);
 }