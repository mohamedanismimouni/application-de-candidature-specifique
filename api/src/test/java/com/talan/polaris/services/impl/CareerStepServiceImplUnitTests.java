package com.talan.polaris.services.impl;

import com.talan.polaris.entities.CareerPathEntity;
import com.talan.polaris.repositories.CareerStepRepository;
import com.talan.polaris.repositories.ProfileRepository;
import com.talan.polaris.services.CareerPathService;
import com.talan.polaris.services.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests class for methods implemented in {@link CareerStepServiceImpl}.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
class CareerStepServiceImplUnitTests {

    @Mock
    private CareerStepRepository careerStepRepository;

    @Mock
    private ProfileService profileService;

    @Mock
    private CareerPathService careerPathService;

    @InjectMocks
    private CareerStepServiceImpl careerStepServiceImpl;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    void deleteCareerStepById_givenCareerStepID_whenCareerStepHasPredecessorAndSucessorCareerSteps_thenCareerStepIsDeleted() {

        // given
        Collection<CareerPathEntity> inboundCareerPaths = new ArrayList<>();
        inboundCareerPaths.add(new CareerPathEntity());
        inboundCareerPaths.add(new CareerPathEntity());

        Collection<CareerPathEntity> outboundCareerPaths = new ArrayList<>();
        outboundCareerPaths.add(new CareerPathEntity());
        outboundCareerPaths.add(new CareerPathEntity());
        outboundCareerPaths.add(new CareerPathEntity());

        Collection<CareerPathEntity> obsoleteCareerPaths = new ArrayList<>();
        obsoleteCareerPaths.addAll(inboundCareerPaths);
        obsoleteCareerPaths.addAll(outboundCareerPaths);

        given(this.careerPathService.findInboundCareerPaths(anyString()))
                .willReturn(inboundCareerPaths);

        given(this.careerPathService.findOutboundCareerPaths(anyString()))
                .willReturn(outboundCareerPaths);

        CareerStepServiceImpl careerStepServiceImplSpy = spy(this.careerStepServiceImpl);

        doNothing().when(careerStepServiceImplSpy).deleteById(anyString());

        // when
        careerStepServiceImplSpy.deleteCareerStepById(anyString());

        // then
        verify(this.careerPathService, times(inboundCareerPaths.size() * outboundCareerPaths.size()))
                .create(any(CareerPathEntity.class));
        verify(this.careerPathService, times(1)).deleteInBatch(obsoleteCareerPaths);
        verify(careerStepServiceImplSpy, times(1)).deleteById(anyString());

    }


    @Test
    void findCareerStepsAssociatedToProfilesWithTeamId_givenTeamId_whenCalled_thenCallProfileRepository() {

        String teamId=UUID.randomUUID().toString();
        // given + when
        this.careerStepServiceImpl.findCareerStepsAssociatedToProfilesWithTeamId(teamId);

        // then
        verify(this.profileService, only()).findCareerStepsAssociatedToProfilesWithTeamId(teamId);

    }

}
