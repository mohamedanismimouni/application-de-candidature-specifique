package com.talan.polaris.services;

import com.talan.polaris.entities.RequestStatusEntity;
import com.talan.polaris.enumerations.RequestStatusEnum;

public interface RequestStatusService{

   public RequestStatusEntity getStatusByLabel(RequestStatusEnum label);
}
