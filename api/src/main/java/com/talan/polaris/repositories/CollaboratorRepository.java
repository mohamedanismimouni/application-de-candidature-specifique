package com.talan.polaris.repositories;
import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.enumerations.ProfileTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;


/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link CollaboratorEntity} domain model.
 *
 * @since 1.0.0
 */
@Repository
public interface CollaboratorRepository extends JpaRepository<CollaboratorEntity, Long> {

    @Query("SELECT collab FROM CollaboratorEntity AS collab WHERE collab.matricule LIKE :matricule")
    public CollaboratorEntity findCollabByMatricule(@Param("matricule") String matricule);
    Boolean existsCollabEntityByMatricule( String matricule);
    CollaboratorEntity findCollabEntityByIdByblos(Long idByblos);
    public Collection<CollaboratorEntity> findByIdNotNull();

    @Query("SELECT collaborator FROM CollaboratorEntity AS collaborator WHERE collaborator.email LIKE :email")
    public Optional<CollaboratorEntity> findUserByEmail(@Param("email") String email);



    @Query("SELECT collaborator FROM CollaboratorEntity collaborator JOIN collaborator.profileTypeEntity p WHERE p.label=:label")
    public Collection<CollaboratorEntity> findUsersByProfileType(
            @Param("label") ProfileTypeEnum label);

    @Query("SELECT collaborator FROM CollaboratorEntity collaborator JOIN collaborator.profileTypeEntity p WHERE p.label=:label AND collaborator.memberOf.id LIKE :teamId")
    public Collection<CollaboratorEntity> findAssignedCollaborators(@Param("label") ProfileTypeEnum label,@Param("teamId") String teamId);

    @Query("SELECT collaborator FROM CollaboratorEntity AS collaborator JOIN collaborator.profileTypeEntity p WHERE p.label=:label AND collaborator.memberOf.id IS NULL")
    public Collection<CollaboratorEntity> findUnassignedCollaborators(@Param("label") ProfileTypeEnum label);

    @Query("SELECT collaborator FROM CollaboratorEntity AS collaborator WHERE "
            + "collaborator.memberOf.id LIKE :teamId "
            + "AND collaborator.id NOT IN "
            + "(SELECT DISTINCT careerPosition.collaborator.id FROM CareerPositionEntity AS careerPosition "
            + "WHERE careerPosition.collaborator.memberOf.id LIKE :teamId)"
            + "ORDER BY collaborator.firstName, collaborator.lastName ASC")
    public Collection<CollaboratorEntity> findUninitializedTeamMembers(@Param("teamId") String teamId);
    
    @Query("SELECT collab FROM CollaboratorEntity AS collab WHERE collab.matricule LIKE :matricule AND collab.firstName LIKE :firstName AND collab.lastName LIKE :lastName ")   
    public CollaboratorEntity findCollabByMatriculeFirstLastName(@Param("matricule") String matricule,@Param("firstName") String firstName,@Param("lastName") String lastName);

    @Query("SELECT collaborator FROM CollaboratorEntity AS collaborator WHERE collaborator.email LIKE :email")
    public CollaboratorEntity findByEmail(@Param("email") String email);

    @Query("SELECT collaborator FROM CollaboratorEntity AS collaborator JOIN collaborator.events e WHERE e.id=:eventId")
    public Collection<CollaboratorEntity> findCollaboratorByEvent(@Param("eventId") Long eventId);


}
