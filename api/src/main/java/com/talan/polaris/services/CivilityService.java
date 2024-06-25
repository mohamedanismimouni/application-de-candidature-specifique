package com.talan.polaris.services;


import com.talan.polaris.entities.CivilityEntity;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link CivilityEntity}.
 *
 * @author Chaima maiza
 * @since 1.0.0
 */
public interface CivilityService {
     CivilityEntity findIdByLabel (String label);

}
