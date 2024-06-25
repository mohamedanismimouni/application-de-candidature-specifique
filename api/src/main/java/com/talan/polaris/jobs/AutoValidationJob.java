package com.talan.polaris.jobs;
import com.talan.polaris.services.DocumentRequestService;
import lombok.SneakyThrows;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
/**
 * AutoValidationJob.
 *
 * @author Imen Mechergui
 * @since 1.1.0
 */
@Component
@DisallowConcurrentExecution
public class AutoValidationJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoValidationJob.class);

    @Autowired
    DocumentRequestService documentRequestService;

    @SneakyThrows(IOException.class)
    @Override
    public void execute(JobExecutionContext context) {
        LOGGER.info("Job ** {} ** starting @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
        documentRequestService.validateDocumentRequestBySystem();
        LOGGER.info("Job ** {} ** completed.  Next job scheduled @ {}", context.getJobDetail().getKey().getName(), context.getNextFireTime());
    }
}
