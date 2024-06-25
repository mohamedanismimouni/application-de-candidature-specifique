package com.talan.polaris.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.ChoiceEntity;
import com.talan.polaris.entities.SelectedChoiceEntity;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link SelectedChoiceEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Repository
public interface SelectedChoiceRepository extends GenericRepository<SelectedChoiceEntity> {

    @Query("SELECT selectedChoice.choice FROM SelectedChoiceEntity AS selectedChoice "
            + "WHERE selectedChoice.response.id LIKE :responseId "
            + "ORDER BY selectedChoice.choice.position ASC")
    public Collection<ChoiceEntity> findChoicesByResponseId(
            @Param("responseId") String responseId);

}
