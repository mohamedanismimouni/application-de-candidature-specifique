package com.talan.polaris.services.impl;


import com.talan.polaris.repositories.RequiredSkillRepository;
import com.talan.polaris.services.RequiredSkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.*;



/**
 * Unit tests class for methods implemented in {@link RequiredSkillServiceImpl}.
 *
 * @author Chaima maiza
 * @since 1.0.0
 */
class RequiredSkillServiceImplUnitTests {


    @Mock
    private RequiredSkillRepository requiredSkillRepository;

    @Mock
    private RequiredSkillService requiredSkillService;

    @InjectMocks
    private RequiredSkillServiceImpl requiredSkillServiceImpl;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }



   @Test
    void  findRequiredSkills_givenProfileIdAndSkillType_whenCalled_thenCallRequiredSkillRepository() {
        // given + when
        this.requiredSkillServiceImpl.findRequiredSkillsByProfileIdAndSkillType(anyString(),any());

        // then
        verify(this.requiredSkillRepository, only()).findRequiredSkillsByProfileIdAndSkillType(anyString(),any());
    }


    @Test
    void findInitializedProfilesByTeamId_givenTeamId_whenCalled_thenCallRequiredSkillRepository() {

        // given + when
        this.requiredSkillServiceImpl.findInitializedProfilesByTeamId(anyString());

        // then
        verify(this.requiredSkillRepository, only()).findInitializedProfilesByTeamId(anyString());

    }
}
