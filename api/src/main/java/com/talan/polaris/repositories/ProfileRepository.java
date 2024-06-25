package com.talan.polaris.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.CareerStepEntity;
import com.talan.polaris.entities.ProfileEntity;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link ProfileEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Repository
public interface ProfileRepository extends GenericRepository<ProfileEntity> {

    @Query("SELECT COUNT(*) FROM ProfileEntity AS profile "
            + "WHERE profile.team.id LIKE :teamId "
            + "AND profile.careerStep.id IN :careerStepsIds")
    public int countProfiles(
            @Param("teamId") String teamId,
            @Param("careerStepsIds") Collection<String> careerStepsIds);

    @Query("SELECT profile FROM ProfileEntity AS profile "
            + "WHERE profile.team.id LIKE :teamId "
            + "ORDER BY profile.label ASC")
    public Collection<ProfileEntity> findProfilesByTeamId(@Param("teamId") String teamId);

    @Query("SELECT profile FROM ProfileEntity AS profile "
            + "WHERE profile.team.id LIKE :teamId "
            + "AND profile.careerStep.id LIKE :careerStepId "
            + "ORDER BY profile.label ASC")
    public Collection<ProfileEntity> findProfilesByTeamIdAndCareerStepId(
            @Param("teamId") String teamId,
            @Param("careerStepId") String careerStepId);

    @Query("SELECT DISTINCT profile.careerStep FROM ProfileEntity AS profile "
            + "WHERE profile.team.id LIKE :teamId "
            + "ORDER BY profile.careerStep.label ASC")
    public Collection<CareerStepEntity> findCareerStepsAssociatedToProfilesWithTeamId(
            @Param("teamId") String teamId);

    @Query("SELECT profile FROM ProfileEntity AS profile "
            + "WHERE profile.label LIKE :profilLabel ")
    public ProfileEntity findProfileByName(@Param("profilLabel") String profilLabel);
}
