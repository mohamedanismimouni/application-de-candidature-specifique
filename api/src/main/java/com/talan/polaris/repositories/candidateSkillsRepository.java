package com.talan.polaris.repositories;

import com.talan.polaris.entities.CandidateSkillsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface candidateSkillsRepository extends JpaRepository<CandidateSkillsEntity, Long> {
}
