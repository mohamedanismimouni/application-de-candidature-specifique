package com.talan.polaris.repositories;

import com.talan.polaris.entities.QualificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link QualificationEntity} domain model.
 *
 * @since 1.0.0
 */
@Repository
public interface QualificationRepository extends JpaRepository<QualificationEntity, Long> {
    QualificationEntity findByLibelle(String label);
    QualificationEntity findByLibelleAndSousQualification(String label,String sousQualification);
}
