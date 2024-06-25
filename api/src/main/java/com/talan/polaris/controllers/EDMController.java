package com.talan.polaris.controllers;
import com.talan.polaris.dto.edmmodelsdto.DocumentDTO;
import com.talan.polaris.services.EDMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
/**
 * A controller defining EDM endpoints.
 * @author Imen Mechergui
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/edm")
public class EDMController {
    @Autowired
    EDMService edmService;

    /**
     * Upload Single File (EDM)
     * @param userID
     * @param parentFolder
     * @param identifier
     * @param file
     * @return
     */
    @PostMapping(path = "/upload/{userId}/{parentFolder}/{identifier}")
    public DocumentDTO uploadFile(@PathVariable(value = "userId", required = true) String userID, @PathVariable(value = "parentFolder", required = true) String parentFolder, @PathVariable(value = "identifier", required = true) String identifier, @RequestParam("file") MultipartFile file) throws IOException {
       return edmService.uploadFileToEDM(userID, parentFolder, identifier, file);
    }


    /**
     * Download File(EDM)
     * @param documentID
     * @return
     */
    @GetMapping (path = "/download/{documentId}")
    public byte[] downloadFile(@PathVariable(value = "documentId", required = true) Long documentID) {
        return edmService.downloadFileFromEDM(documentID);
    }


    @DeleteMapping (path = "/delete/{documentId}")
    public void deleteFile(@PathVariable(value = "documentId", required = true) Long documentID) {
      edmService.deleteFileFromEDM(documentID);
    }
}


