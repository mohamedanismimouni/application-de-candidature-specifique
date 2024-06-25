package com.talan.polaris.services.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talan.polaris.dto.MoodCollabDTO;
import com.talan.polaris.entities.MoodCollab;
import com.talan.polaris.mapper.MoodCollabMapper;
import com.talan.polaris.repositories.MoodCollabRepository;
import com.talan.polaris.services.MoodCollabService;
import com.talan.polaris.services.ScoreService;


@Service
public class MoodCollabServiceImp implements MoodCollabService {

	private final MoodCollabRepository moodCollabRepository ;
	private static final Logger LOGGER = LoggerFactory.getLogger(MoodCollabServiceImp.class);
	private final ModelMapper modelMapper;
	private final ScoreService scoreService ;
    @Autowired
    public MoodCollabServiceImp(MoodCollabRepository moodCollabRepository, ModelMapper modelMapper,ScoreService scoreService ) {
    	this.moodCollabRepository=moodCollabRepository;
    	this.modelMapper = modelMapper;
    	this.scoreService = scoreService;
    
    }
	
	
	
	@Override
	public MoodCollab createMood(MoodCollabDTO moodCollabDTO) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		MoodCollab moodCollab = MoodCollabMapper.convertMoodCollabToEntity(moodCollabDTO, modelMapper);
		moodCollabRepository.save(moodCollab);
		scoreService.increment(moodCollabDTO.getIdCollab(),"How do you feel today" );
		return null;
	}



	@Override
	public List<MoodCollab> findByDate(Long id, Date date) {
		
		return moodCollabRepository.findByDate(id, date) ;
	}



	@Override
	public MoodCollab updateMood(MoodCollabDTO moodCollabDTO,Long id, Date date) {
		MoodCollab moodCollab = (moodCollabRepository.findByDate(id, date)).get(0);
		moodCollab.setIdMood(moodCollabDTO.getIdMood());
		moodCollabRepository.save(moodCollab);
		
		return null;
	}

	@Override
	public MoodCollab findActualMood(Long collaboratorId) {
		Date today = new Date();
	    return moodCollabRepository.findByIdCollabAndDate(collaboratorId, today) ;
	}

}
