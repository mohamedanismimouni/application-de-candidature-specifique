package com.talan.polaris.repositories;
import com.talan.polaris.entities.ParametrageAppliEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * data access operations tied to the {@link ParametrageAppliEntity} domain model.
 * @author Imen Mechergui
 * @since 1.1.0
 */
@Repository
public interface ParametrageAppliRepository extends JpaRepository<ParametrageAppliEntity, Long> {
     ParametrageAppliEntity findByParametre(String parametre);
}
