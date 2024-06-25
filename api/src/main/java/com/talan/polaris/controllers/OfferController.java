package com.talan.polaris.controllers;


import com.talan.polaris.dto.CandidateDTO;
import com.talan.polaris.dto.offerDTO;

import com.talan.polaris.entities.CandidateEntity;

import com.talan.polaris.entities.DepartmentEntity;

import com.talan.polaris.entities.OfferEntity;
import com.talan.polaris.mapper.candidateMapper;
import com.talan.polaris.repositories.offerRepository;
import com.talan.polaris.services.OfferService;
import org.modelmapper.ModelMapper;
import com.talan.polaris.mapper.offerMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offer")
@CrossOrigin(origins = "*" , allowedHeaders = "*")
@Validated
public class OfferController {
    @Autowired
    private OfferService offerService;

    @Autowired
    private com.talan.polaris.repositories.offerRepository offerRepository;


    @PostMapping()
    public OfferEntity saveNewOffer(@RequestBody offerDTO offerDTO){
        return offerService.saveNewOffer(offerDTO);
    }



   @GetMapping("/{id}")
   public ResponseEntity<offerDTO> getOfferById(@PathVariable Long id) throws Exception {
       offerDTO offerById = offerService.getOfferById(id);
       return ResponseEntity.ok().body(offerById);

   }

   @GetMapping("/check/{id}")

   public ResponseEntity<String> offerHasCandidate(@PathVariable Long id) throws Exception{
        long a = offerService.offerHasCandidate(id);
        if (a==0 )
            return ResponseEntity.ok().body("pas de candidats");

        return ResponseEntity.ok().body("il y a des candidats");
   }

    @GetMapping("/allOffers")
    public List<OfferEntity> getAllOffer(){return offerService.getAllOffers();}

    @GetMapping("/ByDepartment/{department}")
    public List<OfferEntity> getOfferByDepartment(@PathVariable("department") String department)
    {return offerService.getByDepartment(department) ;}

    @GetMapping("/AllDepartment")

    public List<DepartmentEntity> getAllDepartments(){return offerService.getAllDepartment();}

    @PutMapping("/update")
    public offerDTO updateOffer(@RequestBody offerDTO offerDTO) {
        OfferEntity offer = offerMapper.convertOfferDTOToEntity(offerDTO, new ModelMapper());
        OfferEntity offerUpdated = this.offerRepository.getOne(offer.getId());
        offerUpdated.setContexte(offer.getContexte());
        offerUpdated.setDepartment(offer.getDepartment());
        offerUpdated.setDescription(offer.getDescription());
        offerUpdated.setSubject(offer.getSubject());
        offerUpdated.setReference(offer.getReference());

        this.offerRepository.save(offerUpdated);
        return offerMapper.convertOfferEntityToDTO(offerUpdated, new ModelMapper());
    }



        @GetMapping("/pageable")
        public ResponseEntity<List<OfferEntity>> findAll ( @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size){
            List<OfferEntity> offers = offerService.findAllPageable(page, size);
            return ResponseEntity.ok().body(offers);

        }

        @DeleteMapping("/delete/{id}")
       public ResponseEntity<offerDTO> deleteOffer (@PathVariable("id") Long id){

            this.offerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);

        }


    }
