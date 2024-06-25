package com.talan.polaris.services.impl;

import com.talan.polaris.entities.CareerPositionEntity;
import com.talan.polaris.entities.EvaluationEntity;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.enumerations.EvaluationStatusEnum;
import com.talan.polaris.repositories.EvaluationRepository;
import com.talan.polaris.services.CareerPositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.mockito.MockitoAnnotations.initMocks;
/**
 * Unit tests class for methods implemented in {@link EvaluationServiceImpl}.
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
public class EvaluationServiceImplUnitTests {

    @Mock
    private EvaluationRepository evaluationRepository;
    @InjectMocks
    private EvaluationServiceImpl evaluationServiceImpl;
    @Mock
    private CareerPositionService careerPositionService;
    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void findEvaluations_givenCareerPositionID_whenCalled_thenCallEvaluationRepository() {
        // given + when
        Long collaboratorId= 1L;
        Long supervisorId= 2L;
        Collection<EvaluationEntity> evaluationsList= this.evaluationServiceImpl.findEvaluations(collaboratorId,supervisorId,EvaluationStatusEnum.OPEN);
        // then
        verify(evaluationRepository, times(1)).findEvaluations(collaboratorId,supervisorId,EvaluationStatusEnum.OPEN);
        assertThat(evaluationsList).isNotNull();
    }

    @Test
    public void findTeamEvaluations_givenTeamIDAndEvaluationStatusEnum_whenCalled_thenCallEvaluationRepository() {
        // given + when
        String teamId= "1";
        Collection<EvaluationEntity> evaluationsList=  this.evaluationServiceImpl.findTeamEvaluations(teamId,EvaluationStatusEnum.OPEN);
        //then
        verify(evaluationRepository, only()).findTeamEvaluations(teamId,EvaluationStatusEnum.OPEN);
        assertThat(evaluationsList).isNotNull();    }

    @Test
    public void createEvaluation_givenEvaluationEntity_whenCalled_thenEvaluationIsCreated() {
        //Evaluation Entity
        EvaluationEntity evaluation = new EvaluationEntity();
        evaluation.setStatus(EvaluationStatusEnum.OPEN);
        evaluation.setEvaluationDate(LocalDate.now());
        evaluation.setSupervisorFeedback("feedBack");
        evaluation.setSupervisorRating(1);
        evaluation.setId( UUID.randomUUID().toString());
        evaluation.setCreatedAt(Instant.now());
        evaluation.setUpdatedAt(Instant.now());
        //spy EvaluationServiceImpl
        EvaluationServiceImpl EvaluationServiceImplSpy = spy(this.evaluationServiceImpl);
        //get result from request
        doAnswer((invocation) -> invocation.getArgument(0))
                .when(EvaluationServiceImplSpy)
                .create(any(EvaluationEntity.class));
        doReturn(new CareerPositionEntity())
                .when(careerPositionService)
                .findById(any());
         Long  carerPositionID =anyLong();
        evaluation.setCareerPosition(careerPositionService.findById(carerPositionID));
        // when
        EvaluationEntity createdEvaluation = EvaluationServiceImplSpy.create(evaluation);
        // then
        assertThat(createdEvaluation).isNotNull();
        assertThat(createdEvaluation.getSupervisorFeedback()).isEqualTo(evaluation.getSupervisorFeedback());
        verify(careerPositionService).findById(carerPositionID);

    }



    @Test
    public void createEvaluation_givenEvaluationEntity_whenCalled_thenThrowIllegalArgumentException() {
        //Evaluation Entity
        Long  carerPositionID =anyLong();
        CareerPositionEntity careerPositionEntity=new CareerPositionEntity();
        careerPositionEntity.setId(carerPositionID);
        EvaluationEntity evaluation = new EvaluationEntity();
        evaluation.setStatus(EvaluationStatusEnum.OPEN);
        evaluation.setEvaluationDate(LocalDate.now());
        evaluation.setCareerPosition(careerPositionEntity);
        evaluation.setSupervisorFeedback("feedBack");
        evaluation.setSupervisorRating(1);
        evaluation.setId( UUID.randomUUID().toString());
        evaluation.setCreatedAt(Instant.now());
        evaluation.setUpdatedAt(Instant.now());
        //spy EvaluationServiceImpl
        EvaluationServiceImpl EvaluationServiceImplSpy = spy(this.evaluationServiceImpl);
        //get result from request
        doAnswer((invocation) -> invocation.getArgument(0))
                .when(EvaluationServiceImplSpy)
                .create(any(EvaluationEntity.class));
        CareerPositionEntity careerPosition=new CareerPositionEntity();
        careerPosition.setStatus(CareerPositionStatusEnum.NEXT);

        doReturn(careerPosition)
                .when(careerPositionService)
                .findById(any());
        //when
        evaluation.setCareerPosition(careerPositionService.findById(carerPositionID));
       //then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> this.evaluationServiceImpl.createEvaluation(evaluation));


    }
}