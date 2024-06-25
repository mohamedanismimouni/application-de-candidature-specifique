package com.talan.polaris.services.impl;


import com.talan.polaris.enumerations.SkillTypeEnum;
import com.talan.polaris.repositories.SkillRepository;
import com.talan.polaris.services.SkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests class for methods implemented in {@link SkillServiceImpl}.
 *
 * @author Chaima maiza
 * @since 1.0.0
 */
class SkillServiceImplUnitTests {


    @Mock
    private SkillRepository skillRepository;

    @Mock
    private SkillService skillService;

    @InjectMocks
    private SkillServiceImpl skillServiceImpl;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    void findSkillsBySkillType_givenHumanSkill_whenCalled_thenCallSkillRepository() {

        // given + when
        this.skillServiceImpl.findSkillsBySkillType(SkillTypeEnum.HUMAN);

        // then
        verify(this.skillRepository, only()).findSkillsBySkillType(SkillTypeEnum.HUMAN);

    }

    @Test
    void findSkillsBySkillType_givenTechnicalSkill_whenCalled_thenCallSkillRepository() {

        // given + when
        this.skillServiceImpl.findSkillsBySkillType(SkillTypeEnum.TECHNICAL);

        // then
        verify(this.skillRepository, only()).findSkillsBySkillType(SkillTypeEnum.TECHNICAL);

    }
}
