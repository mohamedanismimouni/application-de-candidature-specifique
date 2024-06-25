package com.talan.polaris.repositories;

import com.talan.polaris.entities.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface offerRequirementRepository extends JpaRepository<OfferEntity, Long > {
}
