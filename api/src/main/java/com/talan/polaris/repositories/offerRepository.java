package com.talan.polaris.repositories;

import com.talan.polaris.entities.DepartmentEntity;
import com.talan.polaris.entities.OfferEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface offerRepository extends JpaRepository<OfferEntity, Long > {
    List<OfferEntity> findByDepartment(String id);

    @Query("SELECT distinct(O.department) FROM OfferEntity AS O ")
    public List<DepartmentEntity> findAllDepartment();


    @Query(value = "SELECT COUNT(u) FROM candidate_offers u WHERE u.offer_id=?1",nativeQuery = true
    )
    public long offerHasCandidate(Long id);

    Page<OfferEntity> findAll(Pageable pageable);



}
