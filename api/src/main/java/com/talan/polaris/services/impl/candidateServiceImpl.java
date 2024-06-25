package com.talan.polaris.services.impl;

import com.talan.polaris.dto.CandidateDTO;
import com.talan.polaris.entities.*;
import com.talan.polaris.enumerations.CandidacyTypeEnum;
import com.talan.polaris.enumerations.StatusEnum;
import com.talan.polaris.repositories.*;
import com.talan.polaris.services.CvService;
import com.talan.polaris.services.OfferService;
import com.talan.polaris.services.candidateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;

@Service
public class candidateServiceImpl implements candidateService {

    @Autowired
    private candidateRepository candidateRepo;

    @Autowired
    private candidateStatusRepository candidateStatusRepository;

    @Autowired
    private offerRepository offerRepository;

    @Autowired
    private CvService cvService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OfferService offerService;

    @Autowired
    private candidateSkillsRepository candidateSkillsRepository;

    @Autowired
    private TestRepo testRepo;



    @Override
    public CandidateEntity saveCandidate(CandidateEntity candidate) {
        Instant instant1 = Instant.now();
        CandidateStatusEntity candidateStatus = candidateStatusRepository.findByStatusEnum(StatusEnum.TO_QUALIFY);
        candidate.setCreatedAt(instant1);
        candidate.setUpdatedAt(instant1);
        candidate.setCandidateStatusEntity(candidateStatus);
        candidate.getOfferEntities().forEach(offer -> {
            Collection<CandidateEntity> candidates = new HashSet<>();
            Optional<OfferEntity> of = offerRepository.findById(offer.getId());
            candidates = of.get().getCandidates();
            candidates.add(candidate);
            offer.setCandidates(candidates);
            offerRepository.save(offer);
        });
        if (candidate.getOfferEntities().isEmpty()){
            candidate.setCandidacyType(CandidacyTypeEnum.SPONTANEOUS);
        }else {
            candidate.setCandidacyType(CandidacyTypeEnum.ON_JOB);
        }
        CandidateEntity cand = candidateRepo.save(candidate);
        TestEntity test = new TestEntity();
        test.setCandidate(cand);
        test.setSend(false);
        testRepo.save(test);
        return cand;
    }

    @Override
    public HashSet<String> getTestTechnologies(CandidateEntity candidate) {
        Collection<OfferEntity> offerEntities = candidate.getOfferEntities();
        HashSet<String> technologies = new HashSet<>();
        offerEntities.forEach(offer -> {
            Collection<offerRequirementEntity> offerRequirements = offer.getOfferRequirementEntities();
            offerRequirements.forEach(requirement -> {
                technologies.add(requirement.getRequirementName());
            });
        });
        return technologies;
    }

    @Override
    public CandidateEntity getCandidateById(Long id) {
        return candidateRepo.getById(id);
    }




    public byte[] filetoByteArray(String path) {
        byte[] data;
        try {
            InputStream input = new FileInputStream(path);
            int byteReads;
            ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
            while ((byteReads = input.read()) != -1) {
                output.write(byteReads);
            }

            data = output.toByteArray();
            output.close();
            input.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CandidateEntity> getCandidateByCandidacyType(CandidacyTypeEnum candidacyName) {

        return candidateRepo.findByCandidacyType(candidacyName);
    }

    @Override
    public CandidateEntity refuseCandidate(Long idCandidate) {
        Optional<CandidateEntity> candidate = candidateRepo.findById(idCandidate);
        if (candidate.isPresent()){
            CandidateEntity cand = candidate.get();
            cand.setCandidateStatusEntity(candidateStatusRepository.findByStatusEnum(StatusEnum.REJECTED));
            candidateRepo.save(cand);
            return cand;
        }else {
            return null;
        }
    }
}








