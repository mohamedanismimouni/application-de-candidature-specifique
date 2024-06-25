package com.talan.polaris.services;

import java.util.Collection;

import javax.json.JsonPatch;

import com.talan.polaris.dto.OnboardingHint;
import com.talan.polaris.entities.OnboardingEntity;
import com.talan.polaris.exceptions.ResourceNotFoundException;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link OnboardingEntity}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface OnboardingService extends GenericService<OnboardingEntity> {

    /**
     * Finds onboardings having the given {@code freshRecruitId}.
     * 
     * @param freshRecruitId
     * 
     * @return the onboardings matching the given parameter if any.
     */
    public Collection<OnboardingEntity> findOnboardingsByFreshRecruitId(Long freshRecruitId);

    /**
     * Finds an onboarding having the given {@code secretWordPartHolderId} and
     * {@code freshRecruitId}.
     * 
     * @param secretWordPartHolderId
     * @param freshRecruitId
     * 
     * @return the onboarding matching the given parameters if any.
     * 
     * @throws ResourceNotFoundException if no onboarding matching the given 
     * parameters.
     */
    public OnboardingEntity findOnboardingBySecretWordPartHolderIdAndFreshRecruitId(
            Long secretWordPartHolderId,
            Long freshRecruitId);

    /**
     * Gets a hint about the onboardings having the given {@code freshRecruitId}.
     * 
     * @param freshRecruitId
     * 
     * @return the onboarding hint.
     * 
     * @throws ResourceNotFoundException if no onboardings matching the given 
     * parameter.
     */
    public OnboardingHint getOnboardingHint(Long freshRecruitId);

    /**
     * Adds rating to an onboarding given by its {@code onboardingId}.
     * 
     * @param onboardingId
     * @param jsonPatch
     * 
     * @return the updated onboarding.
     * 
     * @throws IllegalArgumentException if the JSON patch application 
     * fails, or if the specified rating is not a number between
     * {@link com.talan.polaris.constants.CommonConstants#ONBOARDING_RATING_MIN} and
     * {@link com.talan.polaris.constants.CommonConstants#ONBOARDING_RATING_MAX}.
     */
    public OnboardingEntity rateOnboarding(String onboardingId, JsonPatch jsonPatch);

}
