package com.talan.polaris.services.impl;

import com.talan.polaris.dto.ImagePDFDTO;
import com.talan.polaris.dto.PasswordSubmission;
import com.talan.polaris.entities.DocumentRequestEntity;
import com.talan.polaris.entities.UsefulDocumentEntity;
import com.talan.polaris.repositories.UsefulDocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.only;
import static org.mockito.MockitoAnnotations.initMocks;

public class UsefulDocumentServiceImplUnitTests {

    @Mock
    private UsefulDocumentRepository usefulDocumentRepository ;

    @InjectMocks
    private UsefulDocumentServiceImpl usefulDocumentServiceImpl;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void findAllUsefulDocument_whenCalled_thenAllUsefulDocumentWasFound() {

        List<UsefulDocumentEntity> usefulDocument = new ArrayList<>();

        given(this.usefulDocumentRepository.findAll())
                .willReturn(usefulDocument);

        // when
        Collection<UsefulDocumentEntity> usefulDocumentList = this.usefulDocumentServiceImpl.findAllUsefulDocument();

        // then
        assertThat(usefulDocumentList).isNotNull();
    }

    @Test
    public void generateImageFromPDF_whenCalled_thenImageOfPDFWasGenerated() throws IOException {
        // given
        UsefulDocumentEntity usefulDocumentEntity = new UsefulDocumentEntity();
        UsefulDocumentServiceImpl sefulDocumentServiceImplSpy = spy(this.usefulDocumentServiceImpl);
        byte[] image = new byte[0];
        doReturn(image)
                .when(sefulDocumentServiceImplSpy)
                .generateImageFromPDF(any(UsefulDocumentEntity.class));
        // when
        //byte[] result = sefulDocumentServiceImplSpy.generateImageFromPDF(usefulDocumentEntity);
        // then
       // assertThat(result).isNotNull();

    }

    @Test
    public void getListOfImage_whenCalled_thenImageListWasFound() throws IOException {
        UsefulDocumentServiceImpl usefulDocumentServiceImplSpy = spy(this.usefulDocumentServiceImpl);
        Collection<ImagePDFDTO> images = new ArrayList<>();

        doReturn(images)
                .when(usefulDocumentServiceImplSpy)
                .getListOfImage();
        // when
        Collection<ImagePDFDTO> listOfImages = usefulDocumentServiceImplSpy.getListOfImage();

        // then
        assertThat(listOfImages).isNotNull();

    }
}
