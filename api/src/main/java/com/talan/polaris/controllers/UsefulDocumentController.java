package com.talan.polaris.controllers;

import com.talan.polaris.dto.ImagePDFDTO;
import com.talan.polaris.dto.UsefulDocumentDTO;
import com.talan.polaris.mapper.UsefulDocumentMapper;
import com.talan.polaris.services.UsefulDocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.*;

@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/usefulDocuments")
public class UsefulDocumentController {
    private final UsefulDocumentService usefulDocumentService;
    private final ModelMapper modelMapper;

    @Autowired
    public UsefulDocumentController(UsefulDocumentService usefulDocumentService, ModelMapper modelMapper) {
        this.usefulDocumentService = usefulDocumentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public Collection<UsefulDocumentDTO> getUsefulDocument(){
        return UsefulDocumentMapper.convertUsefulDocumentEntityListToDTO(this.usefulDocumentService.findAllUsefulDocument(), modelMapper);
    }

    @PostMapping(path = "/updateUsefulDocument")
    public UsefulDocumentDTO saveEDMid(@RequestBody(required = false) UsefulDocumentDTO usefulDocumentDTO) throws IOException {
        return UsefulDocumentMapper.convertUsefulDocumentEntityToDTO(this.usefulDocumentService.update(UsefulDocumentMapper.convertUsefulDocumentDTOToEntity(usefulDocumentDTO, modelMapper)), modelMapper);

    }

    @PostMapping(path = "/generatePDFToImage")
    public void generateFirstUsefulDocumentPage(@RequestBody(required = false) UsefulDocumentDTO usefulDocumentDTO) throws IOException {
        this.usefulDocumentService.generateImageFromPDF(UsefulDocumentMapper.convertUsefulDocumentDTOToEntity(usefulDocumentDTO, modelMapper));
    }

    @PostMapping(path = "/deleteImage")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public void deleteImage(@RequestBody(required = false) UsefulDocumentDTO usefulDocumentDTO)  {
        this.usefulDocumentService.deleteImage(UsefulDocumentMapper.convertUsefulDocumentDTOToEntity(usefulDocumentDTO, modelMapper));
    }

    @GetMapping(path = "/getImages")
    public Collection<ImagePDFDTO> getListOfImage()  {
        return this.usefulDocumentService.getListOfImage();
    }
}
