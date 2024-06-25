package com.talan.polaris.repositories;
import com.talan.polaris.entities.SignatoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * data access operations tied to the {@link SignatoryEntity} domain model.
 * @author Imen Mechergui
 * @since 1.0.0
 */
@Repository
public interface SignataireRepository extends JpaRepository<SignatoryEntity, Long> {
}
