package com.talan.polaris.services.impl;

import com.talan.polaris.entities.QualificationEntity;
import com.talan.polaris.repositories.QualificationRepository;
import com.talan.polaris.services.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QualificationServiceImpl implements QualificationService {
    public final QualificationRepository qualificationRepository;

    @Autowired
    public QualificationServiceImpl(QualificationRepository civilityRepository) {
        this.qualificationRepository=civilityRepository;
    }

    @Override
    public QualificationEntity findIdByLabel(String label) {
        return this.qualificationRepository.findByLibelle(label);
    }

    @Override
    public QualificationEntity findIdByLabelAndSousQualification(String label, String sousQualification) {
        return this.qualificationRepository.findByLibelleAndSousQualification(label,sousQualification);
    }

}
