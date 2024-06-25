package com.talan.polaris.services.impl;

import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.entities.TeamEntity;
import com.talan.polaris.enumerations.AccountStatusEnum;
import com.talan.polaris.enumerations.ProfileTypeEnum;
import com.talan.polaris.repositories.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.time.Instant;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
/**
 * Unit tests class for methods implemented in {@link TeamServiceImpl}.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class TeamServiceImplUnitTests {
    @Mock
    private TeamRepository teamRepository;
    @InjectMocks
    private TeamServiceImpl teamServiceImpl;
    @BeforeEach
    public void setup() {
        initMocks(this);
    }
    @Test
    public void  findTeamByManagerId_givenManagerID_whenCalled_thenCallTeamRepository() {

        // given + when
        this.teamServiceImpl.findTeamByManagerId(anyLong());
        // then
        verify(this.teamRepository, only()).findTeamByManagerId(anyLong());
    }

    @Test
    public void createTeam_givenTeamEntity_whenCalled_thenTeamIsCreated() {
        //Manager Entity
        CollaboratorEntity manager=new CollaboratorEntity();
        manager.setId(1L);
        manager.setFirstName("test");
        manager.setLastName("test");
        //manager.setProfileType(ProfileTypeEnum.MANAGER);
        manager.setCreatedAt(Instant.now());
        manager.setUpdatedAt(Instant.now());
        //manager.setPassword("password");
        manager.setEmail("test@gmail.com");
        manager.setAccountStatus(AccountStatusEnum.ACTIVE);
        //Team Entity
        TeamEntity team = new TeamEntity();
        team.setName("Team A");
        team.setId(UUID.randomUUID().toString());
        team.setCreatedAt(Instant.now());
        team.setUpdatedAt(Instant.now());
        team.setTeamEvaluationDate("10-10-2020");
        team.setManagedBy(manager);
        //spy teamServiceImpl
        TeamServiceImpl teamServiceImplSpy = spy(this.teamServiceImpl);
        //get result from request
        doAnswer((invocation) -> invocation.getArgument(0))
                .when(teamServiceImplSpy)
                .createTeam(any(TeamEntity.class));
        // when
        TeamEntity createdTeam = teamServiceImplSpy.createTeam(team);
        // then
        assertThat(createdTeam).isNotNull();
        assertThat(createdTeam.getName()).isEqualTo(team.getName());
    }

    @Test
    public void updateTeamName_givenTeamName_whenCalled_thenTeamNameIsUpdated() {
       Long managerId = 1L;
       String teamName = "Team A";
        //spy teamServiceImpl
        TeamServiceImpl teamServiceImplSpy = spy(this.teamServiceImpl);
        //get result from request
        doAnswer((invocation) -> invocation.getArgument(0))
                .when(teamServiceImplSpy)
                .updateTeamName(any(Long.class), any(String.class));
        // when
       teamServiceImplSpy.updateTeamName(managerId,teamName);

    }

}
