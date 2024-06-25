package com.talan.polaris.mapper;

import org.modelmapper.ModelMapper;

import com.talan.polaris.dto.MoodCollabDTO;
import com.talan.polaris.entities.MoodCollab;


public class MoodCollabMapper {

	MoodCollabMapper(){
		
	}
	   public static MoodCollab convertMoodCollabToEntity(MoodCollabDTO moodCollabDTO, ModelMapper modelMapper) {
	        return modelMapper.map(moodCollabDTO, MoodCollab.class);
	    }


	    public static MoodCollabDTO convertSkillToDTO(MoodCollab moodCollab, ModelMapper modelMapper) {
	        return modelMapper.map(moodCollab, MoodCollabDTO.class);

	    }
	
}
