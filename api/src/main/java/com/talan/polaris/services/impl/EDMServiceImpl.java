package com.talan.polaris.services.impl;

import com.talan.polaris.common.EDMCommonMethod;
import com.talan.polaris.dto.edmmodelsdto.DocumentDTO;
import com.talan.polaris.services.EDMService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static com.talan.polaris.constants.ConfigurationConstants.*;
import static java.nio.file.Path.*;

/**
 * An implementation of {@link EDMService}, containing business methods
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
@Service
public class EDMServiceImpl implements EDMService {

    @Value("${" + EDM_BASE_PATH + "}")
    String basPathEDM;
    @Value("${" + LOGIN_EDM + "}")
    String loginEDM;
    @Value("${" + PASSWORD_EDM + "}")
    String passwordEDM;
    @Value("${" + EDM_DOWNLOAD_FILE_PATH + "}")
    String dowwnloadSingleFilePathEDM;
    @Value("${" + EDM_UPLOAD_SINGLE_FILE_PATH + "}")
    String uploadSingleFilePathEDM;
    @Value("${" + EDM_DELETE_FILE_PATH + "}")
    String deleteDocumentPathEDM;
    private RestTemplate restTemplate = new RestTemplate();
    private static final Logger LOGGER = LoggerFactory.getLogger(EDMServiceImpl.class);


    /**
     * upload File
     *
     * @param userID
     * @param parentFolder
     * @param identifier
     * @param file
     * @return
     */

    @Override
    public DocumentDTO uploadFileToEDM(String userID, String parentFolder,
                                       String identifier, MultipartFile file) throws IOException {
        File tmpFile = null;
        ResponseEntity<DocumentDTO> response = null;
        try {
            LOGGER.info("Upload File to EDM");
            tmpFile = EDMCommonMethod.convert(file);
            // creating metadata

            MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
            bodyMap.add("file", new FileSystemResource(tmpFile));
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, this.headerRequest(true));

            String path = new StringBuilder()
                    .append(basPathEDM)
                    .append(uploadSingleFilePathEDM)
                    .append(userID)
                    .append("/")
                    .append(parentFolder)
                    .append("/")
                    .append(identifier)
                    .append("/")
                    .append(loginEDM)
                    .toString();
            //call  api
            response = restTemplate.exchange(path,
                    HttpMethod.POST,
                    requestEntity,
                    DocumentDTO.class);


        } catch (IOException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (tmpFile != null) {
                Files.deleteIfExists(of(tmpFile.getAbsolutePath()));
            }
        }

        if (response != null) {
            return response.getBody();

        } else {
            throw new RuntimeException("Null response from EDM");

        }
    }

    /**
     * download File
     *
     * @param documentID
     * @return
     */
    @Override
    public byte[] downloadFileFromEDM(Long documentID) {
        ResponseEntity<byte[]> response = null;
        try {
            LOGGER.info("Download File from EDM");
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(this.headerRequest(false));
            //call api
            String path = new StringBuilder()
                    .append(basPathEDM)
                    .append(dowwnloadSingleFilePathEDM)
                    .append(documentID.toString())
                    .toString();
            response = restTemplate.exchange(path, HttpMethod.GET, requestEntity,
                    byte[].class);
        } catch (NullPointerException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        if (response != null) {
            return response.getBody();

        } else {
            throw new RuntimeException("Null response from EDM");

        }
    }

    /**
     * Delete File
     *
     * @param documentID
     */
    @Override
    public void deleteFileFromEDM(Long documentID) {
        try {
            LOGGER.info("Delete File from EDM");
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(this.headerRequest(false));
            //call  api
            String path = new StringBuilder()
                    .append(basPathEDM)
                    .append(deleteDocumentPathEDM)
                    .append(documentID.toString())
                    .toString();
            restTemplate.exchange(path, HttpMethod.DELETE, requestEntity, Void.class);

        } catch (NullPointerException e) {
            LOGGER.error("error in delete file from EDM"+e.getMessage());
        } catch (Exception ex) {
            LOGGER.error("error in delete file from EDM"+ex.getMessage());
        }
    }


    /**
     * Header authentification
     *
     * @return
     */
    @Override
    public HttpHeaders headerRequest(Boolean body) {

        HttpHeaders headers = new HttpHeaders();
        try {
            LOGGER.info("Prepare Header to connect to EDM");
            //header
            String auth = loginEDM + ":" + passwordEDM;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            headers.set("Authorization", authHeader);
            if (body) {
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return headers;
    }

}
