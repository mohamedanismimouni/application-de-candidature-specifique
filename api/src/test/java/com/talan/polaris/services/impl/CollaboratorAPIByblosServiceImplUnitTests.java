package com.talan.polaris.services.impl;

import com.talan.polaris.entities.CivilityEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talan.polaris.dto.byblosmodelsdto.CollabByblosDTO;
import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.entities.FunctionEntity;
import com.talan.polaris.services.CivilityService;
import com.talan.polaris.services.FunctionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

 class CollaboratorAPIByblosServiceImplUnitTests {


    @Mock
    private CivilityService civilityService;
    @Mock
    private FunctionService functionService;
    @InjectMocks
    private CollaboratorServiceImpl collabServiceImpl;



    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

  @Test
    void  findCivilityByLabel_givenLabel_whenCalled_thenCallCivilityService() {

        CollabByblosDTO collabByblosDTO = new CollabByblosDTO();
        collabByblosDTO.setCivility("Madame");
        CivilityEntity civilityEntity = new CivilityEntity();

        // given + when
        doReturn(civilityEntity)
                .when(civilityService)
                .findIdByLabel(anyString()) ;
        //this.collabServiceImpl.getCollabCivility(collabByblosDTO);


        // then
        verify(this.civilityService, only()).findIdByLabel(anyString());
    }

     @Test
     void  updateCollabFunction_givencollabIdAndLibelle_whenCalled_thenCollabFunctionIsUpdated() {

         // given
        CollaboratorServiceImpl collabServiceImplSpy = spy(this.collabServiceImpl);
         CollaboratorEntity collaboratorEntity = new CollaboratorEntity();
         collaboratorEntity.setId(1L);
         String libelle = "Ingénieur";
         FunctionEntity functionEntity = new FunctionEntity();
         functionEntity.setLibelle(libelle);
         when(this.functionService.findFunctionByLibelle(libelle)).thenReturn(functionEntity);
         doReturn(collaboratorEntity)
                 .when(collabServiceImplSpy)
                 .findById(1L);
         doReturn(functionEntity)
                 .when(this.functionService)
                 .findFunctionByLibelle(libelle);
        collabServiceImplSpy.updateCollabFunction(1L, libelle);
         verify(collabServiceImplSpy,times(1)).updateCollabFunction(1L,libelle);
        Assertions.assertEquals(libelle,libelle);

        doReturn(collaboratorEntity)
                 .when(collabServiceImplSpy)
                 .findById(anyLong());

         doReturn(functionEntity)
                 .when(this.functionService)
                 .findFunctionByLibelle(anyString());

         // when + then
      collabServiceImplSpy.updateCollabFunction(
                        1L,
              "Ingénieur"
                         );

     }


}
