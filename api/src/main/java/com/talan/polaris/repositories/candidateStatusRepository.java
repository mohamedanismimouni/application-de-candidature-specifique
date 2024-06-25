package com.talan.polaris.repositories;

import com.talan.polaris.entities.CandidateStatusEntity;
import com.talan.polaris.enumerations.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface candidateStatusRepository extends JpaRepository<CandidateStatusEntity, Long> {
    @Query("SELECT status FROM CandidateStatusEntity AS status where status.id = :id")
    CandidateStatusEntity findcandidateStatusById(Long id );

    CandidateStatusEntity findByStatusEnum(StatusEnum statusEnum);
}
