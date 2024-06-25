package com.talan.polaris.services.impl;

import com.talan.polaris.entities.CareerPathEntity;
import com.talan.polaris.entities.CareerStepEntity;
import com.talan.polaris.repositories.CareerPathRepository;
import com.talan.polaris.services.CareerStepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
/**
 * Unit tests class for methods implemented in {@link CareerPathServiceImpl}.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
class CareerPathServiceImplUnitTests {

    @Mock
    private CareerPathRepository careerPathRepository;

    @Mock
    private CareerStepService careerStepService;

    @InjectMocks
    private CareerPathServiceImpl careerPathServiceImpl;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    void findInboundCareerPaths_givenCareerStepID_whenCalled_thenCallCareerPathRepository() {

        // given + when
        this.careerPathServiceImpl.findInboundCareerPaths(anyString());

        // then
        verify(this.careerPathRepository, only()).findInboundCareerPaths(anyString());

    }

    @Test
    void findSkillsBySkillType_givenCareerStepID_whenCalled_thenCallCareerPathRepository() {

        // given + when
        this.careerPathServiceImpl.findOutboundCareerPaths(anyString());

        // then
        verify(this.careerPathRepository, only()).findOutboundCareerPaths(anyString());

    }

    @Test
    void createCareerPath_givenCareerPathEntity_whenFromCareerStepIDAndToCareerStepIDAreNull_thenThrowsIllegalArgumentException() {

        // given
        final CareerStepEntity fromCareerStep = new CareerStepEntity();

        final CareerStepEntity toCareerStep = new CareerStepEntity();

        final CareerPathEntity careerPathEntity = new CareerPathEntity();
        careerPathEntity.setFromCareerStep(fromCareerStep);
        careerPathEntity.setToCareerStep(toCareerStep);

        // when + then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> this.careerPathServiceImpl.createCareerPath(careerPathEntity));

    }

    @Test
    void createCareerPath_givenCareerPathEntity_whenFromCareerStepIDIsNullAndToCareerStepIDIsNotNull_thenCareerPathEntityIsCreated() {

        // given
        final CareerStepEntity fromCareerStep = new CareerStepEntity();

        final CareerStepEntity toCareerStep = new CareerStepEntity();
        toCareerStep.setId(UUID.randomUUID().toString());

        final CareerPathEntity careerPathEntity = new CareerPathEntity();
        careerPathEntity.setFromCareerStep(fromCareerStep);
        careerPathEntity.setToCareerStep(toCareerStep);

        CareerPathServiceImpl careerPathServiceImplSpy = spy(this.careerPathServiceImpl);

        doAnswer((invocation) -> invocation.getArgument(0))
                .when(careerPathServiceImplSpy)
                .create(any(CareerPathEntity.class));

        // when
        CareerPathEntity createdCareerPathEntity = careerPathServiceImplSpy.createCareerPath(careerPathEntity);

        // then
        assertThat(createdCareerPathEntity).isNotNull();

    }

    @Test
    void createCareerPath_givenCareerPathEntity_whenFromCareerStepIDIsNotNullAndToCareerStepIDIsNull_thenCareerPathEntityIsCreated() {

        // given
        final CareerStepEntity fromCareerStep = new CareerStepEntity();
        fromCareerStep.setId(UUID.randomUUID().toString());

        final CareerStepEntity toCareerStep = new CareerStepEntity();

        final CareerPathEntity careerPathEntity = new CareerPathEntity();
        careerPathEntity.setFromCareerStep(fromCareerStep);
        careerPathEntity.setToCareerStep(toCareerStep);

        CareerPathServiceImpl careerPathServiceImplSpy = spy(this.careerPathServiceImpl);

        doAnswer((invocation) -> invocation.getArgument(0))
                .when(careerPathServiceImplSpy)
                .create(any(CareerPathEntity.class));

        // when
        CareerPathEntity createdCareerPathEntity = careerPathServiceImplSpy.createCareerPath(careerPathEntity);

        // then
        assertThat(createdCareerPathEntity).isNotNull();

    }

    @Test
    void createCareerPath_givenCareerPathEntity_whenFromCareerStepIDAndToCareerStepIDAreNotNull_thenCareerPathEntityIsCreated() {

        // given
        final CareerStepEntity fromCareerStep = new CareerStepEntity();
        fromCareerStep.setId(UUID.randomUUID().toString());

        final CareerStepEntity toCareerStep = new CareerStepEntity();
        toCareerStep.setId(UUID.randomUUID().toString());

        final CareerPathEntity careerPathEntity = new CareerPathEntity();
        careerPathEntity.setFromCareerStep(fromCareerStep);
        careerPathEntity.setToCareerStep(toCareerStep);

        CareerPathServiceImpl careerPathServiceImplSpy = spy(this.careerPathServiceImpl);

        doAnswer((invocation) -> invocation.getArgument(0))
                .when(careerPathServiceImplSpy)
                .create(any(CareerPathEntity.class));

        // when
        CareerPathEntity createdCareerPathEntity = careerPathServiceImplSpy.createCareerPath(careerPathEntity);

        // then
        assertThat(createdCareerPathEntity).isNotNull();

    }

}
