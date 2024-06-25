package com.talan.polaris.services;

import com.talan.polaris.entities.DocumentRequestEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public interface GenerateDocumentService {
     Long generateDocument (DocumentRequestEntity request) throws Exception;
     Long  addReferenceToDocument (DocumentRequestEntity request)throws Exception;
     public Long EtiquetteGenerate(DocumentRequestEntity request) throws Exception ;
     public void deleteLocalFile(ArrayList<String> attachmentsPath);
     public ArrayList<String> convertByteToFile(Collection<DocumentRequestEntity> requests) throws IOException, Exception;
     public File createEtiquetteMergedFile();
     public File mergeEtiquette (Collection<DocumentRequestEntity> requestEntities, File etiquette, FileOutputStream etiquetteFos)throws Exception;



}
