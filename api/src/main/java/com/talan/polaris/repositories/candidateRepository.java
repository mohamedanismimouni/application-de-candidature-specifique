package com.talan.polaris.repositories;

import com.talan.polaris.entities.CandidateEntity;
import com.talan.polaris.enumerations.CandidacyTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface candidateRepository extends JpaRepository<CandidateEntity, Long > {
    public CandidateEntity findByEmail(String email);
    public CandidateEntity findByPhoneNumber(Long phoneNumber);

    @Query("select c from CandidateEntity as c where c.id = :id")
    CandidateEntity getById(@Param("id") Long id);

    List<CandidateEntity> findByCandidacyType(CandidacyTypeEnum candidacyType);
}
