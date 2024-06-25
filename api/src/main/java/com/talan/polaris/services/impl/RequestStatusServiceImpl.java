package com.talan.polaris.services.impl;

import com.talan.polaris.entities.RequestStatusEntity;
import com.talan.polaris.enumerations.RequestStatusEnum;
import com.talan.polaris.repositories.RequestStatusRepository;
import com.talan.polaris.services.RequestStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestStatusServiceImpl implements RequestStatusService {

    private final RequestStatusRepository requestStatusRepository;

    @Autowired
    public RequestStatusServiceImpl(
            RequestStatusRepository repository)
    {
        this.requestStatusRepository = repository;

    }

    @Override
    public RequestStatusEntity getStatusByLabel(RequestStatusEnum label) {
        return requestStatusRepository.getStatusByLabel(label);
    }
}
