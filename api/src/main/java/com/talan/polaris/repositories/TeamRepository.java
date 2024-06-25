package com.talan.polaris.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.TeamEntity;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link TeamEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Repository
public interface TeamRepository extends GenericRepository<TeamEntity> {

    @Query("SELECT team FROM TeamEntity AS team "
            + "WHERE team.managedBy.id = :managerId")
    public TeamEntity findTeamByManagerId(@Param("managerId") Long managerId);

    @Query("SELECT team FROM TeamEntity AS team "
            + "WHERE team.name = :name")
    public TeamEntity findTeamByName(@Param("name") String name);
}
