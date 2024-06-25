package com.talan.polaris.services;
import java.util.Date;
import java.util.List;

import com.talan.polaris.dto.MoodCollabDTO;
import com.talan.polaris.entities.MoodCollab;

public interface MoodCollabService {

    public MoodCollab createMood (MoodCollabDTO moodCollabDTO);
    public List<MoodCollab> findByDate(Long id ,Date date);
    public MoodCollab updateMood (MoodCollabDTO moodCollabDTO,Long id, Date date);
    public MoodCollab findActualMood(Long collaboratorId);
    
}
