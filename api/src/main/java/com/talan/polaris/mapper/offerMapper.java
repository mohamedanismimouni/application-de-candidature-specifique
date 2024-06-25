package com.talan.polaris.mapper;

import com.talan.polaris.dto.offerDTO;
import com.talan.polaris.entities.OfferEntity;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
public class offerMapper {

    public static OfferEntity convertOfferDTOToEntity (offerDTO offerDTO , ModelMapper modelMapper){

        return modelMapper.map(offerDTO , OfferEntity.class);
    }

    public static  offerDTO convertOfferEntityToDTO(OfferEntity offerEntity , ModelMapper modelMapper){
        return modelMapper.map(offerEntity, offerDTO.class);
    }
}
