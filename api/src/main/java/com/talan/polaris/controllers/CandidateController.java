package com.talan.polaris.controllers;


import com.aspose.pdf.Document;
import com.aspose.pdf.XImage;
import com.aspose.pdf.internal.html.rendering.image.ImageFormat;
import com.talan.polaris.dto.CandidateDTO;
import com.talan.polaris.entities.CandidateEntity;
import com.talan.polaris.entities.CvEntity;
import com.talan.polaris.enumerations.CandidacyTypeEnum;
import com.talan.polaris.mapper.candidateMapper;


import com.talan.polaris.services.CvService;
import com.talan.polaris.services.MailService;
import com.talan.polaris.services.candidateService;


import com.talan.polaris.services.*;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/candidate")
@Validated
public class CandidateController {

    @Autowired
    private candidateService candidateService;

    @Autowired
    private com.talan.polaris.repositories.candidateRepository candidateRepository;

    @Autowired
    private CvService cvService;

    @Autowired
    private MailService mailService;

    @Autowired
    private TestService testService;
    @PostMapping("/indexCv")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file)throws IOException {


        HashMap<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> result = new HashMap<String, Object>();
        String id_cv = "";
        HashSet<String> emails = new HashSet<>();
        String candidateImg=null;
        HashSet<String> skills;
        String phone;
        try {
            result = cvService.save(file);
            skills = (HashSet<String>) result.get("skills");
            id_cv = (String) result.get("id_cv");
            emails = (HashSet<String>) result.get("emails");
            phone = (String) result.get("phone");

            String path= file.getOriginalFilename();
            Document pdfDocument = new Document(file.getBytes());
            XImage xImage = pdfDocument.getPages().get_Item(1).getResources().getImages().get_Item(1);

            FileOutputStream outputImage = new FileOutputStream("C:/Users/hp/OneDrive/Bureau/output.png");

            xImage.save(outputImage, ImageFormat.Png);
            candidateImg =Base64.getEncoder().encodeToString(candidateService.filetoByteArray("C:/Users/hp/OneDrive/Bureau/output.png"));
            outputImage.close();

        } catch (IOException e) {
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("message", "Uploading failed");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            map.put("status", HttpStatus.BAD_REQUEST.value());
            map.put("message", "Invalid file type");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        map.put("status", HttpStatus.OK.value());
        map.put("message", "uploaded successfully !!");
        map.put("id_cv", id_cv);
        map.put("Skills", skills);
        map.put("emails",emails);
        map.put("candidateImg",candidateImg);
        map.put("phone",phone);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping()
    public CandidateDTO saveNewCandidate(@RequestBody CandidateDTO candidateDTO) {
        candidateDTO.getCandidateSkills().forEach(skill -> {
            skill.setCandidateEntity(candidateDTO);
            Instant instant = Instant.now();
            skill.setCreatedAt(instant);
            skill.setUpdatedAt(instant);
        });
        CandidateEntity cand = candidateService.saveCandidate(candidateMapper.convertCandidateDTOToEntity(candidateDTO,new ModelMapper()));
        return candidateMapper.convertCandidateEntityToDTO(cand,new ModelMapper());

    }
    @GetMapping("/sendTest/{id}")
    public boolean sendTest (@PathVariable("id") Long id){
        HashSet<String> tech = new HashSet<>();
        tech.add("html");
        tech.add("css");
        boolean test = testService.sendTest(tech,id);
        return test;
    }

    @GetMapping("/rejectCandidate/{idCandidate}")
    public CandidateEntity rejectCandidate(@PathVariable("idCandidate") Long idCandidate){
        return candidateService.refuseCandidate(idCandidate);
    }

    @GetMapping("/{id}")
    public CandidateEntity getCandidate(@PathVariable("id") Long id) {
        return candidateService.getCandidateById(id);
    }


   @GetMapping("/all")
    public List<CandidateEntity> getAll() {
        List<CandidateEntity> candidates = candidateRepository.findAll();
        return candidates;
    }

    @PutMapping("/update")
    public CandidateDTO updateCandidate(@RequestBody CandidateDTO candidateDTO) {


        CandidateEntity cand = candidateMapper.convertCandidateDTOToEntity(candidateDTO, new ModelMapper());
        CandidateEntity candidatUpdated=this.candidateService.getCandidateById(cand.getId());
        candidatUpdated.setPosteActuel(cand.getPosteActuel());

        candidatUpdated.setDateNaissance(cand.getDateNaissance());
        candidatUpdated.setEmailSecondaire(cand.getEmailSecondaire());
        candidatUpdated.setSocieteActuelle(cand.getSocieteActuelle());
       candidatUpdated.setUniversite(cand.getUniversite());
       candidatUpdated.setCandidateImg(cand.getCandidateImg());
           this.candidateRepository.save(candidatUpdated);
        return candidateMapper.convertCandidateEntityToDTO(candidatUpdated, new ModelMapper());
    }

    @GetMapping("/ExportCv/{id}")
    public byte[] getCvByCandidateId (@PathVariable("id") Long idCandidate){
        CandidateEntity candidate = candidateService.getCandidateById(idCandidate);
        String idCv = candidate.getId_cv();
        CvEntity cv = cvService.getCvById(idCv);
        String encoded = cv.getEncoded();
        byte[] file = Base64.getDecoder().decode(encoded);
        return file;
    }
    @GetMapping("/candidatesByCandidacyType/{type}")
    public List<CandidateEntity>  getCandidatesByCandidacyType(@PathVariable("type")String candidacyType){
        if(candidacyType.equals("spontaneous")) {
            return candidateService.getCandidateByCandidacyType(CandidacyTypeEnum.SPONTANEOUS);
        }
        return candidateService.getCandidateByCandidacyType(CandidacyTypeEnum.ON_JOB);
    }




}

