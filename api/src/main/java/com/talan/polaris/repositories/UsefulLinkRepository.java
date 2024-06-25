package com.talan.polaris.repositories;
import com.talan.polaris.entities.UsefulLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * data access operations tied to the {@link UsefulLinkEntity} domain model.
 * 
 * @author Imen Mechergui
 * @since 2.0.0
 */
@Repository
public interface UsefulLinkRepository extends JpaRepository<UsefulLinkEntity, Long> {

}
