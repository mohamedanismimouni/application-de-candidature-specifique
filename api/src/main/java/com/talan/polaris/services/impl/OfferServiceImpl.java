package com.talan.polaris.services.impl;

import com.talan.polaris.dto.offerDTO;
import com.talan.polaris.entities.DepartmentEntity;
import com.talan.polaris.entities.OfferEntity;
import com.talan.polaris.exceptions.ResourceNotFoundException;
import com.talan.polaris.mapper.offerMapper;
import com.talan.polaris.repositories.DepartmentRepository;
import com.talan.polaris.repositories.offerRepository;
import com.talan.polaris.services.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private offerRepository offerRepository;

    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public OfferEntity saveNewOffer(offerDTO offerDTO) {
        OfferEntity offer = new OfferEntity();
        offer = offerMapper.convertOfferDTOToEntity(offerDTO,new ModelMapper());
        Instant instant =  Instant.now();
        offer.setCreatedAt(instant);
        offer.setUpdatedAt(instant);
        offerRepository.save(offer);
        return offer ;
    }

    @Override
    public OfferEntity save(OfferEntity offer){
        return offerRepository.save(offer);
    }
    @Override
    public List<OfferEntity> getAllOffers() {

        return offerRepository.findAll();
    }

    @Override
    public OfferEntity updateOffer(offerDTO offerDTO, Long offerId) throws ResourceNotFoundException {
       OfferEntity offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found for this id :: " + offerId));

        offer.setSubject(offerDTO.getSubject());
        offer.setDescription(offerDTO.getDescription());
        offer.setReference(offerDTO.getReference());
        offer.setContexte(offerDTO.getContexte());

        final OfferEntity updatedOffer = offerRepository.save(offer);
        return offer ;

    }

    @Override
    public void delete(Long id) {
        if (offerRepository.offerHasCandidate(id)==0)
        offerRepository.deleteById(id);

    }

    @Override
    public offerDTO getOfferById(Long id) throws Exception {
        OfferEntity offer = offerRepository.findById(id).orElseThrow(() -> new Exception("offer not found"));
         return offerMapper.convertOfferEntityToDTO(offer , new ModelMapper());



    }

    @Override
    public long offerHasCandidate(Long id) {
        return offerRepository.offerHasCandidate(id);

    }

    @Override
    public List<OfferEntity> findAllPageable(int page,int size) {

            List<OfferEntity> offers = new ArrayList<OfferEntity>();
            Pageable paging = PageRequest.of(page, size);

            Page<OfferEntity> pageOffers = offerRepository.findAll( paging);
            offers = pageOffers.getContent();

            return offers;
    }

    @Override
    public List<OfferEntity> getByDepartment(String departmentId) {
        return offerRepository.findByDepartment(departmentId);
    }


    @Override
    public List<DepartmentEntity> getAllDepartment (){return departmentRepository.findAll();}
}