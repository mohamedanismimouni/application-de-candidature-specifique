package com.talan.polaris.services;

import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.entities.CollaboratorEntity;
import java.util.Collection;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link CollabEntity}.
 *
 * @author Chaima maiza
 * @since 1.0.0
 */
public interface CollaboratorAPIByblosService {
    public Collection<CollaboratorEntity> synchronizeCollaborators();

    public Collection<CollaboratorEntity> findAllCollabWithUser();

  /*  public CollabEntity findCollabByMatricule(String matricule);
    public Collection<CollabEntity> findAll();
    public CollabEntity update(CollabEntity collabEntity);
    public CollabEntity save(CollabEntity collabEntity );
    public CollabEntity findCollabByIdByblos(Long idByblos);
    public CollabEntity findCollabById(Long id);
    public Boolean existsCollabEntityByMatricule(String matricule);
    public Collection<CollabEntity> synchronizeCollaborators();
    public CollabEntity findById(Long id);
    public void updateCollabFunction(Long collabId, String functionLibelle);
    public Collection<CollabEntity> findAllCollabWithUser();*/
  
    public CollaboratorEntity findCollabByMatriculeFirstLastName(String matricule, String firstName,  String lastName);

}
