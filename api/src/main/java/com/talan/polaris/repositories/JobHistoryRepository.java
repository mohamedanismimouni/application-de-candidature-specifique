package com.talan.polaris.repositories;


import com.talan.polaris.entities.JobHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistoryEntity, Long> {
}
