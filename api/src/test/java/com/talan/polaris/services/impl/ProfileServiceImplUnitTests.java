package com.talan.polaris.services.impl;

import com.talan.polaris.entities.CareerStepEntity;
import com.talan.polaris.entities.ProfileEntity;
import com.talan.polaris.entities.TeamEntity;
import com.talan.polaris.repositories.ProfileRepository;
import com.talan.polaris.services.RequiredSkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
/**
 * Unit tests class for methods implemented in {@link ProfileServiceImpl}.
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
public class ProfileServiceImplUnitTests {

    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private RequiredSkillService requiredSkillService;
    @InjectMocks
    private ProfileServiceImpl profileServiceImpl;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void findProfilesByTeamId_givenTeamID_whenCalled_thenProfilesAreFound() {
        // given
        String teamID= "1";
        // given + when
        Collection<ProfileEntity> profilesList = this.profileServiceImpl.findProfilesByTeamId(teamID, true);
        // then
        verify(requiredSkillService, times(1)).findInitializedProfilesByTeamId(teamID);
        assertThat(profilesList).isNotNull();
    }

    @Test
    public void findCareerStepsAssociatedToProfilesWithTeamId_givenTeamID_whenCalled_thenCallProfileRepository() {
        // given + when
        this.profileServiceImpl.findCareerStepsAssociatedToProfilesWithTeamId(anyString());

        // then
        verify(this.profileRepository, only()).findCareerStepsAssociatedToProfilesWithTeamId(anyString());
    }

    @Test
    public void createProfile_givenProfileEntity_whenCalled_thenProfileIsCreated() {
        //Profile Entity
        ProfileEntity profile = new ProfileEntity();
        profile.setLabel("Backend developer Java");
        profile.setCareerStep(new CareerStepEntity());
        profile.setTeam(new TeamEntity());
        profile.setId( UUID.randomUUID().toString());
        profile.setCreatedAt(Instant.now());
        profile.setUpdatedAt(Instant.now());
        //spy profileServiceImpl
        ProfileServiceImpl profileServiceImplSpy = spy(this.profileServiceImpl);
        //get result from request
        doAnswer((invocation) -> invocation.getArgument(0))
                .when(profileServiceImplSpy)
                .create(any(ProfileEntity.class));
        // when
        ProfileEntity createdPorfile = profileServiceImplSpy.create(profile);
        // then
        assertThat(createdPorfile).isNotNull();
        assertThat(createdPorfile.getLabel()).isEqualTo(profile.getLabel());
    }

    @Test
    public void findProfilesByTeamIdAndCareerStepID_givenTeamIDdAndCareerStepId_whenCalled_thenCallProfileRepository() {
        // given + when
        this.profileServiceImpl.findProfilesByTeamIdAndCareerStepId(anyString(), anyString());
        // then
        verify(this.profileRepository, only()).findProfilesByTeamIdAndCareerStepId(anyString(), anyString());


    }

}



