package com.talan.polaris.services.impl;

import com.talan.polaris.entities.RequestTypeEntity;
import com.talan.polaris.enumerations.RequestTypeEnum;
import com.talan.polaris.repositories.RequestTypeRepository;
import com.talan.polaris.services.RequestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class RequestTypeServiceImpl
        implements RequestTypeService {

    private final RequestTypeRepository requestTypeRepository;
    private static final String DEFAULT_SORT_FIELD_NAME = "createdAt";

    @Autowired
    public RequestTypeServiceImpl(
            RequestTypeRepository repository)
    {
        this.requestTypeRepository = repository;

    }

    @Override
    public RequestTypeEntity getTypeByLabel(RequestTypeEnum label) {
        return this.requestTypeRepository.getTypeByLabel(label);
    }

    @Override
    public Collection<RequestTypeEntity> findRequestType() {
        return  this.requestTypeRepository.findAll(Sort.by(Sort.Direction.DESC, DEFAULT_SORT_FIELD_NAME));    }

    @Override
    public RequestTypeEntity update(RequestTypeEntity requestTypeEntity) {
        return this.requestTypeRepository.saveAndFlush(requestTypeEntity);
    }

    @Override
    public Date payRollLastUpload(RequestTypeEnum requestType) {
        RequestTypeEntity payRollRequest=requestTypeRepository.getTypeByLabel(requestType);
        return payRollRequest.getUploadDate();
    }

    @Override
    public Collection<RequestTypeEntity> getVisibleRequestType() {
        return requestTypeRepository.getDocumentTypeByVisibility(true);
    }
    @Override
    public Collection<RequestTypeEntity> getTemplateRequestType() {
        return requestTypeRepository.getDocumentTypeByVisibilityAndTemplate(true,true);
    }

}
