/*
package com.talan.polaris.repositories;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.talan.polaris.entities.UserEntity;
import com.talan.polaris.enumerations.ProfileTypeEnum;
*/
/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link UserEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 *//*

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT user FROM UserEntity AS user WHERE user.email LIKE :email")
    public Optional<UserEntity> findUserByEmail(@Param("email") String email);

    */
/*@Query("SELECT user FROM HRResponsibleEntity AS user")
    public Collection<UserEntity> getHRResponsible();
*//*

   */
/* @Query("SELECT user FROM UserEntity AS user WHERE user.profileType LIKE :profileType")
    public Collection<UserEntity> findUsersByProfileType(
            @Param("profileType") ProfileTypeEnum profileType);*//*


    @Query("SELECT user FROM UserEntity user JOIN user.profileTypeEntity p WHERE p.label=:label")
    public Collection<UserEntity> findUsersByProfileType(
            @Param("label") ProfileTypeEnum label);

*/
/*    @Query("SELECT collaborator FROM CollaboratorEntity AS collaborator "
            + "WHERE collaborator.memberOf.id LIKE :teamId")
    public Collection<UserEntity> findAssignedCollaborators(@Param("teamId") String teamId);

    @Query("SELECT collaborator FROM CollaboratorEntity AS collaborator "
            + "WHERE collaborator.memberOf.id IS NULL")
    public Collection<UserEntity> findUnassignedCollaborators();

    @Query("SELECT collaborator FROM CollaboratorEntity AS collaborator "
            + "WHERE collaborator.memberOf.id LIKE :teamId "
            + "AND collaborator.id NOT IN "
            + "(SELECT DISTINCT careerPosition.collaborator.id FROM CareerPositionEntity AS careerPosition "
            + "WHERE careerPosition.collaborator.memberOf.id LIKE :teamId)"
            + "ORDER BY collaborator.firstName, collaborator.lastName ASC")
    public Collection<UserEntity> findUninitializedTeamMembers(@Param("teamId") String teamId);*//*


    @Query("SELECT collaborator FROM UserEntity collaborator JOIN collaborator.profileTypeEntity p WHERE p.label=:label AND collaborator.memberOf.id LIKE :teamId")
    public Collection<UserEntity> findAssignedCollaborators(@Param("label") ProfileTypeEnum label,@Param("teamId") String teamId);

    @Query("SELECT collaborator FROM UserEntity AS collaborator JOIN collaborator.profileTypeEntity p WHERE p.label=:label AND collaborator.memberOf.id IS NULL")
    public Collection<UserEntity> findUnassignedCollaborators(@Param("label") ProfileTypeEnum label);

    @Query("SELECT collaborator FROM UserEntity AS collaborator JOIN collaborator.profileTypeEntity p WHERE p.label=:label "
            + "AND collaborator.memberOf.id LIKE :teamId "
            + "AND collaborator.id NOT IN "
            + "(SELECT DISTINCT careerPosition.collaborator.id FROM CareerPositionEntity AS careerPosition "
            + "WHERE careerPosition.collaborator.memberOf.id LIKE :teamId)"
            + "ORDER BY collaborator.firstName, collaborator.lastName ASC")
    public Collection<UserEntity> findUninitializedTeamMembers(@Param("label") ProfileTypeEnum label, @Param("teamId") String teamId);

}
*/
