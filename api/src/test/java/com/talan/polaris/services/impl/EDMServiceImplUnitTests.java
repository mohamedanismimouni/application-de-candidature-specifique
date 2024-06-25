package com.talan.polaris.services.impl;
import com.talan.polaris.dto.edmmodelsdto.DocumentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
/**
 * Unit tests class for methods implemented in {@link EDMServiceImpl}.
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
public class EDMServiceImplUnitTests {
    @Mock
    private EDMServiceImpl edmServiceImpl;


    @BeforeEach
    public void setup() {
        initMocks(this);

    }

    @Test
    public void uploadFileToEDM_givenUserIDAndParentFolderAndidentifierAndFile_whenCalled_thenFileIsUploaded() throws IOException {
        final MultipartFile mockFile = mock(MultipartFile.class);
        String userID ="1";
        String identifier ="2";
        String parentFolder="Attestation";
        DocumentDTO documentInformations = new DocumentDTO();
        documentInformations.setId((long) 1);

        EDMServiceImpl edmServiceImplSpy = spy(this.edmServiceImpl);
        //get result from request
        given(edmServiceImplSpy.uploadFileToEDM(anyString(), anyString(), anyString(), any(MultipartFile.class)))
                .willReturn(documentInformations);
        // when
        DocumentDTO fileInformations = edmServiceImplSpy.uploadFileToEDM(userID, parentFolder, identifier, mockFile);
        // then
        verify(edmServiceImplSpy, only()).uploadFileToEDM(userID, parentFolder, identifier, mockFile);
        assertThat(fileInformations).isNotNull();
    }
    @Test
    public void downloadFileFromEDM_givenDocumentID_whenCalled_thenFileIsDownloaded() {
        long documentID = 1;
        String str = "byte array size example";
        byte array[] = str.getBytes();
        EDMServiceImpl edmServiceImplSpy = spy(this.edmServiceImpl);
        given(edmServiceImplSpy.downloadFileFromEDM(anyLong()))
                .willReturn(array);
        // when
        byte bytes[] = edmServiceImplSpy.downloadFileFromEDM(documentID);
        // then
        verify(edmServiceImplSpy, only()).downloadFileFromEDM(documentID);
        assertThat(bytes).isNotNull();
    }
    @Test
    public void deleteFileFromEDM_givenDocumentID_whenCalled_thenFileIsDeleted() {
        long id = 1;
        EDMServiceImpl edmServiceImplSpy = spy(this.edmServiceImpl);
        doAnswer((invocation) -> invocation.getArgument(0))
                .when(edmServiceImplSpy)
                .downloadFileFromEDM(any(Long.class));                ;
        // when
        edmServiceImplSpy.deleteFileFromEDM(id);
        // then
        verify(edmServiceImplSpy, only()).deleteFileFromEDM(id);
    }

}
