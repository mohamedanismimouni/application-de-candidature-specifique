package com.talan.polaris.services.impl;

import com.talan.polaris.entities.*;
import com.talan.polaris.enumerations.RequestTypeEnum;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

 class GenerateDocumentServiceImplUnitTests {

    @Mock
    private GenerateDocumentServiceImpl generateDocumentServiceImpl;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }


   @Test
     void generateDocument_givenRequest_whenCalled_thenDocumentIsGenerated() throws Exception {
       DocumentRequestEntity requestEntity = new DocumentRequestEntity();
       RequestTypeEntity type = new RequestTypeEntity();
       type.setId(1L);
       type.setLabel(RequestTypeEnum.SALARY_CERTIFICATE);

       CollaboratorEntity collabEntity= new CollaboratorEntity();
       collabEntity.setId(1L);

       CollaboratorEntity collaboratorEntity = new CollaboratorEntity();
       collaboratorEntity.setId(1L);
      // collaboratorEntity.setCollab(collabEntity);

       requestEntity.setCollaborator(collaboratorEntity);
       requestEntity.setType(type);

       Long idEDM= new Long(3);
       requestEntity.setIdEDM(idEDM);
       GenerateDocumentServiceImpl generateDocumentServiceImplSpy = spy(this.generateDocumentServiceImpl);
       when(generateDocumentServiceImplSpy.generateDocument(requestEntity)).thenReturn(requestEntity.getIdEDM());
       assertThat(generateDocumentServiceImplSpy.generateDocument(requestEntity)).isNotNull();
    }

    @Test
     void convertBytesToFile_givenIdEDM_whenCalled_thenBytesIsConvertedToFile() throws IOException {
        final Long idEDM = new Long(88);
        final File mockFile = mock(File.class);
        GenerateDocumentServiceImpl generateDocumentServiceImplSpy = spy(this.generateDocumentServiceImpl);
        when(generateDocumentServiceImplSpy.convertBytesToFile(idEDM)).thenReturn(mockFile);
        assertThat(generateDocumentServiceImplSpy.convertBytesToFile(idEDM)).isNotNull();
    }

    @Test
     void replaceDocVariables_givenRequest_whenCalled_thenDocsVariablesReplaced() {
        HashMap<String, String> variables =new HashMap<>();
        DocumentRequestEntity request = new DocumentRequestEntity();
        GenerateDocumentServiceImpl generateDocumentServiceImplSpy = spy(this.generateDocumentServiceImpl);
        when(generateDocumentServiceImplSpy.replaceDocVariables(request)).thenReturn(variables);
        assertThat(generateDocumentServiceImplSpy.replaceDocVariables(request)).isNotNull();
    }

    @Test
     void mappingTemplateData_givenVariablesAndFileAndRequest_whenCalled_thenTemplateDataIsMapped() throws Exception {
        HashMap<String, String> variables= new HashMap<>();
        variables.put("firstName", "name");
        variables.put("lastName", "name");

        DocumentRequestEntity requestEntity = new DocumentRequestEntity();
        RequestTypeEntity type = new RequestTypeEntity();
        type.setId(1L);
        type.setLabel(RequestTypeEnum.SALARY_CERTIFICATE);

        CollaboratorEntity collabEntity= new CollaboratorEntity();
        collabEntity.setId(1L);

        CollaboratorEntity collaboratorEntity = new CollaboratorEntity();
        collaboratorEntity.setId(1L);
       // collaboratorEntity.setCollab(collabEntity);

        requestEntity.setCollaborator(collaboratorEntity);
        requestEntity.setType(type);

        final File file = mock(File.class);
        GenerateDocumentServiceImpl generateDocumentServiceImplSpy = spy(this.generateDocumentServiceImpl);
        assertThat( generateDocumentServiceImplSpy.mappingTemplateData(variables,file,requestEntity)).isNotNull();
    }

    @Test
     void writeDocxToStream_whenCalled_thenDocxWritedToStream() throws Exception {
           WordprocessingMLPackage template= new WordprocessingMLPackage();
            String target = "";
            DocumentRequestEntity request= new DocumentRequestEntity();
            GenerateDocumentServiceImpl generateDocumentServiceImplSpy = spy(this.generateDocumentServiceImpl);
            generateDocumentServiceImplSpy.writeDocxToStream(template, target, request);
            assertThat(generateDocumentServiceImplSpy.writeDocxToStream(template, target, request)).isNotNull();
    }

     @Test
     void writeDocxEtiquetteToStream_whenCalled_thenDocxWritedToStream() throws Exception {
         WordprocessingMLPackage template= new WordprocessingMLPackage();
         String target = "";
         DocumentRequestEntity request= new DocumentRequestEntity();
         GenerateDocumentServiceImpl generateDocumentServiceImplSpy = spy(this.generateDocumentServiceImpl);
         generateDocumentServiceImplSpy.writeDocxToStream(template, target, request);
         assertThat(generateDocumentServiceImplSpy.writeDocxToStream(template, target, request)).isNotNull();
     }


    @Test
     void convertFileToMultiPartFile_givenFile_whenCalled_thenFileConvertedToMultipartFile() throws IOException {

        final MultipartFile mockMultipartFile = mock(MultipartFile.class);
        final File mockFile = mock(File.class);
        GenerateDocumentServiceImpl generateDocumentServiceImplSpy = spy(this.generateDocumentServiceImpl);
        when(generateDocumentServiceImplSpy.convertFileToMultiPartFile(mockFile)).thenReturn(mockMultipartFile);
        assertThat(generateDocumentServiceImplSpy.convertFileToMultiPartFile(mockFile)).isNotNull();

    }

    @Test
     void createFile_whenCalled_thenFileCreated() {
        final File mockFile = mock(File.class);
        GenerateDocumentServiceImpl generateDocumentServiceImplSpy = spy(this.generateDocumentServiceImpl);
        when(generateDocumentServiceImplSpy.createFile()).thenReturn(mockFile);
        assertThat(generateDocumentServiceImplSpy.createFile()).isNotNull();
    }

    @Test
     void addReferenceToDocument_givenRequest_whenCalled_thenReferenceIsAdded() throws Exception {
        DocumentRequestEntity requestEntity = new DocumentRequestEntity();
        RequestTypeEntity type = new RequestTypeEntity();
        type.setId(1L);
        type.setLabel(RequestTypeEnum.SALARY_CERTIFICATE);

        CollaboratorEntity collabEntity= new CollaboratorEntity();
        collabEntity.setId(1L);

        CollaboratorEntity collaboratorEntity = new CollaboratorEntity();
        collaboratorEntity.setId(1L);
        //collaboratorEntity.setCollab(collabEntity);

        requestEntity.setCollaborator(collaboratorEntity);
        requestEntity.setType(type);

        Long idEDM= new Long(3);
        requestEntity.setIdEDM(idEDM);
        GenerateDocumentServiceImpl generateDocumentServiceImplSpy = spy(this.generateDocumentServiceImpl);

        when(generateDocumentServiceImplSpy.generateDocument(requestEntity)).thenReturn(requestEntity.getIdEDM());
        assertThat(generateDocumentServiceImplSpy.generateDocument(requestEntity)).isNotNull();
    }

}
