package com.talan.polaris.repositories;
import com.talan.polaris.entities.DocumentRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.talan.polaris.enumerations.RequestTypeEnum;
import org.springframework.data.jpa.repository.Query;
import com.talan.polaris.enumerations.RequestStatusEnum;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Collection;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link DocumentRequestEntity} domain model.
 *
 * @since 1.0.0
 */


public interface DocumentRequestRepository extends JpaRepository<DocumentRequestEntity, Long> {

    @Query("SELECT documentRequestEntity FROM DocumentRequestEntity AS documentRequestEntity "
            + "WHERE (documentRequestEntity.collaborator.id = :collaboratorId) AND (documentRequestEntity.type.id = :typeId) AND (documentRequestEntity.status.label <> :status) ORDER BY documentRequestEntity.createdAt DESC")
    public Collection<DocumentRequestEntity> findDocumentRequestsByCollaboratorIdAndTypeId(
               Long collaboratorId , Long typeId ,RequestStatusEnum status);


    @Query("SELECT request FROM DocumentRequestEntity AS request WHERE request.status.label LIKE :status ORDER BY request.createdAt DESC")
    public Collection<DocumentRequestEntity> getDocumentsRequestsByStatus(@Param("status") RequestStatusEnum status);

    @Query("SELECT request FROM DocumentRequestEntity AS request WHERE request.status.label LIKE :status1 OR request.status.label LIKE :status2 OR request.status.label LIKE :status3 "
            +"ORDER BY request.createdAt DESC")
    public Collection<DocumentRequestEntity> getProcessedDocuments( @Param("status1") RequestStatusEnum status1, @Param("status2") RequestStatusEnum status2 , @Param("status3") RequestStatusEnum status3);


    @Query("SELECT COUNT(*)  FROM DocumentRequestEntity AS documentRequestEntity "
            + "WHERE (documentRequestEntity.type.label LIKE :type) AND (documentRequestEntity.validatedAt >= :date) AND (documentRequestEntity.status.label LIKE :status)")
    public int countDocumentsByType(@Param("type") RequestTypeEnum type , @Param("date") Instant date ,@Param("status") RequestStatusEnum status );

    @Query("SELECT request FROM DocumentRequestEntity AS request WHERE request.status.label LIKE :status AND request.type.label LIKE :type AND request.withoutTemplate=:template")
    public Collection<DocumentRequestEntity> getRequestsWithoutTemplate( @Param("status") RequestStatusEnum status, @Param("type") RequestTypeEnum type,@Param("template") Boolean template);


    @Query("SELECT request FROM DocumentRequestEntity AS request WHERE request.status.label LIKE :status AND request.withoutTemplate = false ORDER BY request.createdAt DESC")
    public Collection<DocumentRequestEntity> getInWaitingRequestsWithTemplate(@Param("status") RequestStatusEnum status);

    @Query("SELECT request FROM DocumentRequestEntity AS request WHERE request.collaborator.id= :collabId AND request.status.label LIKE :status ORDER BY request.updatedAt DESC")
    public Collection<DocumentRequestEntity> getCollabsRequestsByStatus(@Param("collabId") Long collabId, @Param("status") RequestStatusEnum status);

    @Query("SELECT request FROM DocumentRequestEntity AS request WHERE request.collaborator.id= :collabId ORDER BY request.updatedAt DESC")
    public Collection<DocumentRequestEntity> getCollabRequests(@Param("collabId") Long collabId);


    @Query("select count(e) from DocumentRequestEntity e where e.status.label LIKE :statusEnum AND e.collaborator.id= :collabId")
    long countDocumentBySatus(RequestStatusEnum statusEnum, Long collabId);

}



