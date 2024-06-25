package com.talan.polaris.services.impl;



import com.talan.polaris.constants.CommonConstants;
import com.talan.polaris.entities.*;
import com.talan.polaris.repositories.JobHistoryRepository;

import com.talan.polaris.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.Instant;


import static com.talan.polaris.constants.ConfigurationConstants.*;


@Configuration
@EnableScheduling
@Service
public class JobHistoryServiceImpl implements JobHistoryService {

    private final JobHistoryRepository jobHistoryRepository;

    private final CollaboratorAPIByblosService collaboratorAPIByblosService;
    private static final Logger LOGGER = LoggerFactory.getLogger(JobHistoryServiceImpl.class);
    @Autowired
    ProverbService proverbService;


    @Autowired
    public JobHistoryServiceImpl(JobHistoryRepository jobHistoryRepository, CollaboratorAPIByblosService collaboratorAPIByblosService) {
        this.jobHistoryRepository = jobHistoryRepository;
        this.collaboratorAPIByblosService = collaboratorAPIByblosService;
    }

    /**
     * job to load new collaborators
     * at 12:00 AM every day
     */
    @Scheduled(cron = "${" + LOAD_COLLAB_CRON + "}", zone = "Europe/Paris")
    public void loadCollaboratorsFromByblosJob ()
    {

        JobHistoryEntity jobHistoryEntity = new JobHistoryEntity();
        //Job name
        jobHistoryEntity.setJobName("Load collaborators from byblos");

        try {
            //set job start date
            Instant jobStart = Instant.now();
            jobHistoryEntity.setStartDate(jobStart);
            LOGGER.info("job Load collaborators from byblos start at {} ",jobStart);

            this.collaboratorAPIByblosService.synchronizeCollaborators();

            //set job end date
            Instant jobEnd =Instant.now();
            jobHistoryEntity.setEndDate(jobEnd);

            //calculate duration
            Duration between = Duration.between(jobStart, jobEnd);
            jobHistoryEntity.setJobDuration(between.getNano());
            LOGGER.info("job load collaborators from byblos end at {} ",jobEnd);

            LOGGER.info("job duration load collaborators from byblos end at {}",jobHistoryEntity.getJobDuration());

            //set job status
            jobHistoryEntity.setJobStatus(CommonConstants.JOB_STATUS_OK);

            this.jobHistoryRepository.save(jobHistoryEntity);
            LOGGER.info("job excuted at with succes");

        }catch (Exception e)
        {
            //set ko in job status if there is exception
            jobHistoryEntity.setJobStatus(CommonConstants.JOB_STATUS_KO);
            LOGGER.info("job added at with KO and Exception :{}",e.getMessage());
            this.jobHistoryRepository.save(jobHistoryEntity);
        }
    }


    @Override
    public JobHistoryEntity saveJobHistory(JobHistoryEntity jobHistoryEntity){
        return this.jobHistoryRepository.save(jobHistoryEntity);
    }

    @Scheduled(cron = "${" + PROVERB_CRON + "}", zone = "Europe/Paris")
    public void publishedProverb ()
    {
        JobHistoryEntity jobHistory =new JobHistoryEntity();
        try {
            //set job start date
            Instant jobStart = Instant.now();
            jobHistory.setStartDate(jobStart);

            jobHistory.setJobName("Published Proverb");
            LOGGER.info("Start job publishedProverb");
            proverbService.publishedProverb();
            //set job end date
            Instant jobEnd =Instant.now();
            jobHistory.setEndDate(jobEnd);

            //calculate duration
            Duration between = Duration.between(jobStart, jobEnd);
            jobHistory.setJobDuration(between.getNano());
            jobHistory.setJobStatus(CommonConstants.JOB_STATUS_OK);

            this.jobHistoryRepository.save(jobHistory);
            LOGGER.info("END job publishedProverb with OK");
        }catch (Exception e){
            //set ko in job status if there is exception
            jobHistory.setJobStatus(CommonConstants.JOB_STATUS_KO);
            LOGGER.info("job added at with KO and Exception : {} ",e.getMessage());
            this.jobHistoryRepository.save(jobHistory);
        }

    }

}
