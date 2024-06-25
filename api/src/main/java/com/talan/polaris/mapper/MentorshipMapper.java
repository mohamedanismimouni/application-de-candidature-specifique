package com.talan.polaris.mapper;

import com.talan.polaris.dto.MentorshipDTO;
import com.talan.polaris.entities.MentorshipEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Mapping Mentorship Entity
 *
 * @author Imen mechergui
 * @since 1.0.0
 */
public class MentorshipMapper {
    private MentorshipMapper() {

    }

    /**
     * convert Mentorship DTO To Entity
     *
     * @param mentorshipDTO
     * @param modelMapper
     * @return MentorshipEntity
     */
    public static MentorshipEntity convertMentorshipDTOToEntity(MentorshipDTO mentorshipDTO, ModelMapper modelMapper) {

        return modelMapper.map(mentorshipDTO, MentorshipEntity.class);
    }

    /**
     * convert Mentorship Entity To DTO
     *
     * @param mentorshipEntity
     * @param modelMapper
     * @return MentorshipDTO
     */
    public static MentorshipDTO convertMentorshipEntityToDTO(MentorshipEntity mentorshipEntity, ModelMapper modelMapper) {
        return modelMapper.map(mentorshipEntity, MentorshipDTO.class);

    }

    /**
     * convert Mentorship Entity List To DTO
     *
     * @param mentorshipEntityList
     * @param modelMapper
     * @return Collection<MentorshipDTO>
     */
    public static Collection<MentorshipDTO> convertMentorshipEntityListToDTO(Collection<MentorshipEntity> mentorshipEntityList, ModelMapper modelMapper) {
        Type mentorshipListType = new TypeToken<List<MentorshipDTO>>() {
        }.getType();
        return modelMapper.map(mentorshipEntityList, mentorshipListType);

    }
}
