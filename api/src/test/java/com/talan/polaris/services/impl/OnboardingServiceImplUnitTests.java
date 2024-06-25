package com.talan.polaris.services.impl;

import com.talan.polaris.entities.OnboardingEntity;
import com.talan.polaris.exceptions.ResourceNotFoundException;
import com.talan.polaris.repositories.OnboardingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class OnboardingServiceImplUnitTests {

    @Mock
    private OnboardingRepository onboardingRepository;

    @InjectMocks
    private OnboardingServiceImpl onboardingServiceImpl;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }


    @Test
    void findOnboardingsByFreshRecruitId_givenRecruitId_whenCalled_thenCallOnboardingRepository() {

        // given + when
        this.onboardingServiceImpl.findOnboardingsByFreshRecruitId(anyLong());

        // then
        verify(this.onboardingRepository, only()).findOnboardingsByFreshRecruitId(anyLong());
    }

    @Test
    void findOnboardingsByFreshRecruitId_givenRecruitId_whenEntityIsNotFound_thenEntityNotFound() {

        // given
        Long id = 1L;
        Collection<OnboardingEntity> onboardingEntities = new ArrayList<>();
        given(this.onboardingRepository.findOnboardingsByFreshRecruitId(id)).willReturn((onboardingEntities));

        // when
        Collection<OnboardingEntity> foundEntity = this.onboardingServiceImpl.findOnboardingsByFreshRecruitId(id);

        // then
        assertThat(foundEntity).size().isEqualTo(0);
    }


    @Test
    void findOnboardingBySecretWordPartHolderIdAndFreshRecruitId_givenSecretWordPartHolderIdAndFreshRecruitId_whenEntityIsNotFound_thenThrowsResourceNotFoundException() {

        // given + when
        given(this.onboardingRepository.findOnboardingBySecretWordPartHolderIdAndFreshRecruitId(anyLong(), anyLong()))
                .willReturn(Optional.ofNullable(null));
        // then
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> this.onboardingServiceImpl.findById(anyString()));
    }

    @Test
    void getOnboardingHint_givenFreshRecruitId_whenIsNotFound_thenThrowsResourceNotFoundException() {

        // given + when
        Long id= 1L;
        Collection<OnboardingEntity> onboardingEntities = new ArrayList<>();
        given(this.onboardingRepository.findOnboardingsByFreshRecruitId(id)).willReturn((onboardingEntities));

        // then
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> this.onboardingServiceImpl.getOnboardingHint(anyLong()));
    }


}
