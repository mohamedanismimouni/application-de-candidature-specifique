package com.talan.polaris.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.json.JsonException;
import javax.json.JsonPatch;
import javax.json.JsonStructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.talan.polaris.dto.OnboardingHint;
import com.talan.polaris.entities.OnboardingEntity;
import com.talan.polaris.exceptions.ResourceNotFoundException;
import com.talan.polaris.repositories.OnboardingRepository;
import com.talan.polaris.services.OnboardingService;

import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_JSON_PATCH;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_ONBOARDING_PROCESS_RATING_CONSTRAINT;
import static com.talan.polaris.constants.CommonConstants.ONBOARDING_RATING_MIN;
import static com.talan.polaris.constants.CommonConstants.ONBOARDING_RATING_MAX;

/**
 * An implementation of {@link OnboardingService}, containing business methods
 * implementations specific to {@link OnboardingEntity}, and may override some
 * of the common methods' implementations inherited from
 * {@link GenericServiceImpl}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class OnboardingServiceImpl
        extends GenericServiceImpl<OnboardingEntity>
        implements OnboardingService {

    private final OnboardingRepository onboardingRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public OnboardingServiceImpl(
            OnboardingRepository repository,
            ObjectMapper objectMapper) {

        super(repository);
        this.onboardingRepository = repository;
        this.objectMapper = objectMapper;

    }

    @Override
    public Collection<OnboardingEntity> findOnboardingsByFreshRecruitId(Long freshRecruitId) {
        return this.onboardingRepository.findOnboardingsByFreshRecruitId(freshRecruitId);
    }

    @Override
    public OnboardingEntity findOnboardingBySecretWordPartHolderIdAndFreshRecruitId(
            Long secretWordPartHolderId,
            Long freshRecruitId) {
        return this.onboardingRepository.findOnboardingBySecretWordPartHolderIdAndFreshRecruitId(
                secretWordPartHolderId,
                freshRecruitId).orElseThrow(ResourceNotFoundException::new);

    }

    @Override
    public OnboardingHint getOnboardingHint(Long freshRecruitId) {

        Collection<OnboardingEntity> onboardings = this.findOnboardingsByFreshRecruitId(freshRecruitId);

        if (onboardings.isEmpty())
            throw new ResourceNotFoundException();

        int secretWordLength = 0;
        Collection<String> secretWordPartsHoldersNames = new ArrayList<>();

        for (OnboardingEntity onboarding : onboardings) {

            secretWordLength += onboarding.getSecretWordPart().length();
            secretWordPartsHoldersNames.add(
                    onboarding.getSecretWordPartHolder().getFirstName()
                    + ' '
                    + onboarding.getSecretWordPartHolder().getLastName());

        }

        return new OnboardingHint(secretWordLength, secretWordPartsHoldersNames);

    }

    @Override
    @Transactional
    public OnboardingEntity rateOnboarding(String onboardingId, JsonPatch jsonPatch) {

        OnboardingEntity onboarding = findById(onboardingId);

        JsonStructure targetJson = objectMapper.convertValue(
                new OnboardingEntity(),
                JsonStructure.class);

        JsonStructure patchedJson;
        try {
            patchedJson = jsonPatch.apply(targetJson);
        } catch (JsonException e) {
            throw new IllegalArgumentException(ERROR_BAD_REQUEST_JSON_PATCH, e);
        }

        OnboardingEntity patchedOnboarding = this.objectMapper.convertValue(
                patchedJson,
                OnboardingEntity.class);

        if (patchedOnboarding.getRating() == null || 
                patchedOnboarding.getRating().compareTo(ONBOARDING_RATING_MIN) < 0 ||
                patchedOnboarding.getRating().compareTo(ONBOARDING_RATING_MAX) > 0) {

            throw new IllegalArgumentException(
                    ERROR_BAD_REQUEST_ONBOARDING_PROCESS_RATING_CONSTRAINT);

        }

        onboarding.setRating(patchedOnboarding.getRating());

        return onboarding;

    }

}
