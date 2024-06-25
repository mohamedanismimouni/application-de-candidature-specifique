package com.talan.polaris.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.OnboardingEntity;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link OnboardingEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Repository
public interface OnboardingRepository extends GenericRepository<OnboardingEntity> {

    @Query("SELECT onboarding FROM OnboardingEntity AS onboarding "
            + "WHERE onboarding.freshRecruit.id = :freshRecruitId")
    public Collection<OnboardingEntity> findOnboardingsByFreshRecruitId(
            @Param("freshRecruitId") Long freshRecruitId);

    @Query("SELECT onboarding FROM OnboardingEntity AS onboarding "
            + "WHERE onboarding.secretWordPartHolder.id = :secretWordPartHolderId "
            + "AND onboarding.freshRecruit.id = :freshRecruitId")
    public Optional<OnboardingEntity> findOnboardingBySecretWordPartHolderIdAndFreshRecruitId(
            @Param("secretWordPartHolderId") Long secretWordPartHolderId,
            @Param("freshRecruitId") Long freshRecruitId);

}
