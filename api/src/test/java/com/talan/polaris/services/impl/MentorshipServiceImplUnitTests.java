package com.talan.polaris.services.impl;

import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.entities.MentorshipEntity;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.enumerations.MentorshipStatusEnum;
import com.talan.polaris.repositories.MentorshipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
/**
 * Unit tests class for methods implemented in {@link MentorshipServiceImpl}.
 * 
 * @author Imen Mechergui
 * @since 1.0.0
 */
public class MentorshipServiceImplUnitTests {

    @Mock
    private MentorshipRepository mentorshipRepository;
    @InjectMocks
    private MentorshipServiceImpl mentorshipServiceImpl;
    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void findMentorshipsByCareerPositionId_givenCareerPositionID_whenCalled_thenCallMentorshipRepository() {
        // given + when
        Long CareerPositionID= anyLong();
        Collection<MentorshipEntity> profilesList= this.mentorshipServiceImpl.findMentorshipsByCareerPositionId(CareerPositionID);
        // then
        verify(mentorshipRepository, times(1)).findMentorshipsByCareerPositionId(CareerPositionID);
        assertThat(profilesList).isNotNull();
    }

    @Test
    public void findMentorshipBySkillId_givenSkillIDAndCareerPositionId_whenCalled_thenCallMentorshipRepository() {

        // given + when
        this.mentorshipServiceImpl.findMentorshipBySkillIdAndCareerPositionId(anyString(),anyLong());
        // then
        verify(this.mentorshipRepository, only()).findMentorshipBySkillIdAndCareerPositionId(anyString(),anyLong());
    }
    @Test
    public void findInitializedTeamMembers_givenCareerPositionID_whenCalled_thenCallMentorshipRepository() {
        // given + when
        String teamId= UUID.randomUUID().toString();
        String profileId= UUID.randomUUID().toString();
        String careerStepId= UUID.randomUUID().toString();
        Long supervisorId= 1L;
        Long mentorId=1L;
        Collection<CollaboratorEntity> usersList= this.mentorshipServiceImpl.findInitializedTeamMembers(
                teamId,
                profileId,
                careerStepId,
                supervisorId,
                CareerPositionStatusEnum.CURRENT,
                mentorId,
                MentorshipStatusEnum.ACTIVE,
                LocalDate.now());
        // then
        verify(mentorshipRepository, times(1)).findInitializedTeamMembers (teamId,
                profileId,
                careerStepId,
                supervisorId,
                CareerPositionStatusEnum.CURRENT,
                mentorId,
                MentorshipStatusEnum.ACTIVE);;
        assertThat(usersList).isNotNull();
    }
    @Test
    public void findMentorshipsByMentorId_givenMentorId_whenCalled_thenCallMentorshipRepository() {
        // given + when
        this.mentorshipServiceImpl.findMentorshipsByMentorId(anyLong());
        // then
        verify(this.mentorshipRepository, only()).findMentorshipsByMentorId(anyLong());
    }
}
