package com.talan.polaris.repositories;

import com.talan.polaris.entities.RequestStatusEntity;
import com.talan.polaris.enumerations.RequestStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RequestStatusRepository extends JpaRepository<RequestStatusEntity, Long> {
    @Query("SELECT status FROM RequestStatusEntity AS status WHERE status.label LIKE :label")
    public RequestStatusEntity getStatusByLabel(@Param("label") RequestStatusEnum label);

}
