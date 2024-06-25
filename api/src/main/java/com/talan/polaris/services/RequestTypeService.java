package com.talan.polaris.services;

import com.talan.polaris.entities.RequestTypeEntity;
import com.talan.polaris.enumerations.RequestTypeEnum;

import java.util.Collection;
import java.util.Date;

/**
 * RequestTypeService
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
public interface RequestTypeService{
    public RequestTypeEntity getTypeByLabel(RequestTypeEnum label);
    public Collection<RequestTypeEntity> findRequestType();

    public RequestTypeEntity update(RequestTypeEntity requestTypeEntity);
    public Date payRollLastUpload(RequestTypeEnum requestType) ;
    public Collection<RequestTypeEntity>  getVisibleRequestType();
    public Collection<RequestTypeEntity> getTemplateRequestType();


}
