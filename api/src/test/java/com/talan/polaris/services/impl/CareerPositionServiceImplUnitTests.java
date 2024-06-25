package com.talan.polaris.services.impl;



import com.talan.polaris.repositories.CareerPositionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import static org.mockito.MockitoAnnotations.initMocks;

class CareerPositionServiceImplUnitTests {

    @Mock
    private CareerPositionRepository careerPositionRepository;


    @InjectMocks
    private CareerPositionServiceImpl careerPositionServiceImpl;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }


    @Test
    void findCareerPositionsByCollaboratorIdAndStatus_givenCollaboratorIdAndStatus_whenCalled_thenCallCareerPositionRepository() {
        // given + when
        this.careerPositionServiceImpl.findCareerPositionsByCollaboratorIdAndStatus(anyLong(), any());

        // then
        verify(this.careerPositionRepository, only()).findCareerPositionsByCollaboratorIdAndStatus(anyLong(), any());
    }





}
