package com.talan.polaris.repositories;


import com.talan.polaris.entities.CivilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link CivilityEntity} domain model.
 *
 * @since 1.0.0
 */
@Repository
public interface CivilityRepository extends JpaRepository<CivilityEntity, Long> {
     CivilityEntity findByAbrev(String label);
}
