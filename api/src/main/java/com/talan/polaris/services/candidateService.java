package com.talan.polaris.services;

import com.talan.polaris.dto.CandidateDTO;
import com.talan.polaris.entities.CandidateEntity;
import com.talan.polaris.enumerations.CandidacyTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashSet;
import java.util.List;


public interface candidateService {

    public CandidateEntity saveCandidate(CandidateEntity candidate);
    HashSet<String> getTestTechnologies(CandidateEntity candidate);

    CandidateEntity getCandidateById(Long id);

    public byte[] filetoByteArray(String path);

    List<CandidateEntity> getCandidateByCandidacyType(CandidacyTypeEnum candidacyName);

    CandidateEntity refuseCandidate(Long idCandidate);

}
