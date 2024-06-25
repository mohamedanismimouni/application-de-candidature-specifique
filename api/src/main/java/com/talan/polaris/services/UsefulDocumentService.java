package com.talan.polaris.services;

import com.talan.polaris.dto.ImagePDFDTO;
import com.talan.polaris.entities.UsefulDocumentEntity;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface UsefulDocumentService {
    public Collection<UsefulDocumentEntity> findAllUsefulDocument();
    public UsefulDocumentEntity update(UsefulDocumentEntity usefulDocumentEntity) throws IOException;
    public File convertBytesToFile(Long idEDM) throws IOException;
    public void generateImageFromPDF(UsefulDocumentEntity usefulDocumentEntity) throws IOException;
    public void deleteImage(UsefulDocumentEntity usefulDocumentEntity);
    public Collection<ImagePDFDTO> getListOfImage();
}
