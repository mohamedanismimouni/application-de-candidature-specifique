package com.talan.polaris.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talan.polaris.entities.JobHistoryEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.scheduling.support.CronSequenceGenerator;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class JobHistoryServiceImplUnitTests {


    @InjectMocks
    private JobHistoryServiceImpl jobHistoryServiceImpl;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        initMocks(this);
    }


    @Test
    void createJobHistory_givenJobHistoryEntity_whenCalled_thenJobHistoryIsCreated() {
        //jobHistory Entity
        JobHistoryEntity jobHistoryEntity = new JobHistoryEntity();
        jobHistoryEntity.setJobDuration(1234L);
        jobHistoryEntity.setJobStatus("OK");
        jobHistoryEntity.setJobName("api byblos");
        jobHistoryEntity.setEndDate(Instant.now());
        jobHistoryEntity.setStartDate(Instant.now());
        jobHistoryEntity.setId(1234L);

        //spy jobHistoryServiceImp
        JobHistoryServiceImpl jobHistoryServiceImplSpy = spy(this.jobHistoryServiceImpl);

        //get result from request
        doAnswer((invocation) -> invocation.getArgument(0))
                .when(jobHistoryServiceImplSpy)
                .saveJobHistory(any(JobHistoryEntity.class));
        // when
        JobHistoryEntity createdJobHistory = jobHistoryServiceImplSpy.saveJobHistory(jobHistoryEntity);
        // then
        assertThat(createdJobHistory).isNotNull();
        assertThat(createdJobHistory.getJobName()).isEqualTo(jobHistoryEntity.getJobName());
    }



    @Test
    void checkCronTime_givenNextTimeOfCron_thenIsOK() {
        // given + when
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator("0 0 0 * * *", TimeZone.getTimeZone("Europe/Paris"));
        ZonedDateTime date = LocalDateTime.of(2021, 4, 1, 9, 52, 0).atZone(ZoneId.of("Europe/Paris"));
        ZonedDateTime expected = LocalDateTime.of(2021, 4, 2, 0, 0, 0).atZone(ZoneId.of("Europe/Paris"));
        // then
        assertThat(cronSequenceGenerator.next(Date.from(date.toInstant()))).isEqualTo(Date.from(expected.toInstant()));
    }




}
