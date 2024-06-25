package com.talan.polaris.services;

import com.talan.polaris.dto.offerDTO;
import com.talan.polaris.entities.DepartmentEntity;
import com.talan.polaris.entities.OfferEntity;

import java.util.List;

public interface OfferService {

    public OfferEntity saveNewOffer(offerDTO offerDTO);
    public OfferEntity save(OfferEntity offer);
    public List<OfferEntity> getAllOffers();

    OfferEntity updateOffer(offerDTO offerDTO, Long offerId);

    void delete(Long id);

    offerDTO getOfferById(Long id) throws Exception;

    long offerHasCandidate(Long id);

    List<OfferEntity> findAllPageable(int page,int size);
    List<OfferEntity> getByDepartment(String department);
    List<DepartmentEntity> getAllDepartment ();
}
