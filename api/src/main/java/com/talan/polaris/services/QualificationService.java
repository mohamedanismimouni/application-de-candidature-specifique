package com.talan.polaris.services;

import com.talan.polaris.entities.QualificationEntity;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link QualificationEntity}.
 *
 * @author Chaima maiza
 * @since 1.0.0
 */
public interface QualificationService{
    public QualificationEntity findIdByLabel (String label);
    public QualificationEntity findIdByLabelAndSousQualification (String label , String sousQualification);
}
