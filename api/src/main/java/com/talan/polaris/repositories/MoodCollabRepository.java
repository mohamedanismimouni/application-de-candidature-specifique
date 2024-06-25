package com.talan.polaris.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

import com.talan.polaris.entities.MoodCollab;


@Repository
public interface MoodCollabRepository extends JpaRepository<MoodCollab, Long>  {
@Query("select u from MoodCollab u where u.idCollab =?1 and u.date =?2")
public List<MoodCollab> findByDate(Long id , Date date);

     MoodCollab findByIdCollabAndDate(Long id , Date date);
	
	
	
}

