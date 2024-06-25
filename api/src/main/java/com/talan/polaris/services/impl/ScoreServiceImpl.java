package com.talan.polaris.services.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.repositories.CollaboratorRepository;
import com.talan.polaris.repositories.ConfigScoreRepository;
import com.talan.polaris.services.ScoreService;


@Service
public class ScoreServiceImpl implements ScoreService {
	
	
	private final CollaboratorRepository collabRepository ;
	private final ConfigScoreRepository configScoreRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(MoodCollabServiceImp.class);
	
    @Autowired
    public ScoreServiceImpl(CollaboratorRepository collabRepository, ConfigScoreRepository configScoreRepository) {
    	this.collabRepository=collabRepository;
    	this.configScoreRepository = configScoreRepository;
    }

	@Override
	public Integer increment(Long id, String componentLabel) {
		CollaboratorEntity collab=null;
		try {
			collab = collabRepository.findById(id).get();
			collab.setScore(collab.getScore() + configScoreRepository.findByComponentLabel(componentLabel).getComponentScore());
			collabRepository.save(collab);
		}
		catch(Exception ex)
			{
				LOGGER.error(ex.getMessage());
			}
		return collab.getScore();
	}

	@Override
	public Integer getScore(String componentLabel) {
		// TODO Auto-generated method stub
		return configScoreRepository.findByComponentLabel(componentLabel).getComponentScore();
	}

	@Override
	public Integer getScoreByUserEmail(String email) {
    	CollaboratorEntity collaboratorEntity = this.collabRepository.findByEmail(email);
		return collaboratorEntity.getScore() ;
	}

	/**
	 * decrement collab score
	 * @param id
	 * @param componentLabel
	 * @return
	 */
	@Override
	public Integer decrement(Long id, String componentLabel) {
		CollaboratorEntity collab=null;
		try {
			collab = collabRepository.findById(id).get();
			int score = collab.getScore() - configScoreRepository.findByComponentLabel(componentLabel).getComponentScore();
			if (score < 0) {
				collab.setScore(0);

			} else {
				collab.setScore(score);
			}
			collabRepository.save(collab);
		}
		catch(Exception ex)
		{
			LOGGER.error(ex.getMessage());
		}
		return collab.getScore();
	}



}
