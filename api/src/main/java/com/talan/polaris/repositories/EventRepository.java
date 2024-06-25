package com.talan.polaris.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long>{
	
	@Query("SELECT event FROM EventEntity AS event order by event.date DESC")
	public Collection<EventEntity> findUpcomingEvents();
	
	@Query("SELECT event FROM EventEntity AS event WHERE event.date < CURRENT_DATE")
	public Collection<EventEntity> findPastEvents();

	@Query("SELECT event FROM EventEntity AS event WHERE event.date < CURRENT_DATE AND event.status LIKE 'ACTIVE'")
	public Collection<EventEntity> findActivePastEvents();

}
