package com.talan.polaris.repositories;
import com.talan.polaris.entities.ProverbEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Collection;
import java.util.List;

/**
 *
 * data access operations tied to the {@link ProverbEntity} domain model.
 * @author Imen Mechergui
 * @since 2.0.0
 */
public interface ProverbRepository extends JpaRepository<ProverbEntity, Long> {
    @Query("SELECT proverb FROM ProverbEntity AS proverb  WHERE published = false ORDER BY proverb.createdAt DESC ")
     Collection<ProverbEntity> getProverbs();

    @Query("SELECT proverb FROM ProverbEntity AS proverb  WHERE published = true ORDER BY proverb.createdAt ASC ")
     List<ProverbEntity> getPublishedProverb();

}
