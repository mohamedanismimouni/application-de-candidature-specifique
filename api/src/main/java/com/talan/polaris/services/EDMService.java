package com.talan.polaris.services;
import com.talan.polaris.dto.edmmodelsdto.DocumentDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * EDMService.
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
public interface EDMService {


    public DocumentDTO uploadFileToEDM(String userID, String parentFolder, String identifier, MultipartFile file) throws IOException;
    public byte[] downloadFileFromEDM(Long documentID);
    public void deleteFileFromEDM(Long documentID);
    public HttpHeaders headerRequest(Boolean body) ;


    }
