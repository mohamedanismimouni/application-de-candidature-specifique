package com.talan.polaris.services.impl;


import com.talan.polaris.entities.*;
import com.talan.polaris.repositories.AcquiredSkillRepository;
import com.talan.polaris.services.AcquiredSkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests class for methods implemented in {@link AcquiredSkillServiceImpl}.
 *
 * @author Chaima maiza
 * @since 1.0.0
 */
class AcquiredSkillServiceImplUnitTests {
    @Mock
    private AcquiredSkillRepository acquiredSkillRepository;

    @Mock
    private AcquiredSkillService acquiredSkillService;

    @InjectMocks
    private AcquiredSkillServiceImpl acquiredSkillServiceImpl;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }


    @Test
    void findAcquiredSkillsByCollaboratorId_givenCollaboratorId_whenCalled_thenCallAcquiredSkillRepository() {
        // given + when
        this.acquiredSkillServiceImpl.findAcquiredSkillsByCollaboratorId(anyLong());

        // then
        verify(this.acquiredSkillRepository, only()).findAcquiredSkillsByCollaboratorId(anyLong());
    }

    @Test
     void findAcquiredSkillsByCollaboratorIds_givenCollaboratorIds_whenCalled_thenCallAcquiredSkillRepository() {
        // given + when
        Collection<Long> collaboratorsIds = new ArrayList<>();
        this.acquiredSkillServiceImpl.findAcquiredSkillsByCollaboratorsIds(collaboratorsIds);

        // then
        verify(this.acquiredSkillRepository, only()).findAcquiredSkillsByCollaboratorsIds(collaboratorsIds);
    }



    @Test
    void createAcquiredSkills_givenAcquiredSkillIdAndAcquiredSkillLevelEntity_whenCalled_thenCallAcquiredSkillRepository() {

        // given
        AcquiredSkillServiceImpl acquiredSkillServiceImplSpy = spy(this.acquiredSkillServiceImpl);
        Collection<AcquiredSkillEntity> acquiredSkillEntities = new ArrayList<>();
        doAnswer((invocation) -> invocation.getArgument(0))
                .when(acquiredSkillServiceImplSpy)
                .create(any(AcquiredSkillEntity.class));

        // then
        Collection<AcquiredSkillEntity> CreatedAcquiredSkillEntities = acquiredSkillServiceImplSpy.createAcquiredSkills(acquiredSkillEntities);
        assertThat(CreatedAcquiredSkillEntities).isNotNull();

    }


}
