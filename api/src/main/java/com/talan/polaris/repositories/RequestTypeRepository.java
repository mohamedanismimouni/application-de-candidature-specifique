package com.talan.polaris.repositories;

import com.talan.polaris.entities.ParametrageAppliEntity;
import com.talan.polaris.entities.RequestTypeEntity;
import com.talan.polaris.enumerations.RequestTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;


/**
 *
 * data access operations tied to the {@link ParametrageAppliEntity} domain model.
 * @author Imen Mechergui
 * @since 1.0.0
 */
public interface RequestTypeRepository extends JpaRepository<RequestTypeEntity, Long> {
    @Query("SELECT type FROM RequestTypeEntity AS type WHERE type.label LIKE :label")
    public RequestTypeEntity getTypeByLabel(@Param("label") RequestTypeEnum label);

    @Query("SELECT type FROM RequestTypeEntity AS type WHERE type.visibility = :visibility ORDER BY type.uploadDate DESC")
    public Collection<RequestTypeEntity>  getDocumentTypeByVisibility(@Param("visibility") Boolean visibility);

    @Query("SELECT type FROM RequestTypeEntity AS type WHERE type.visibility = :visibility AND type.isTemplateOfRequest = :isTemplateOfRequest ORDER BY type.uploadDate DESC")
    public Collection<RequestTypeEntity>  getDocumentTypeByVisibilityAndTemplate(@Param("visibility") Boolean visibility,
                                                                                 @Param("isTemplateOfRequest") Boolean isTemplateOfRequest);
}
