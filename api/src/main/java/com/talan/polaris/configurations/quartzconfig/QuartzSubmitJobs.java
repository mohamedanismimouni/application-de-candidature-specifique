//package com.talan.polaris.configurations.quartzconfig;
//import com.talan.polaris.constants.ConfigurationConstants;
//import com.talan.polaris.jobs.AutoValidationJob;
//import org.quartz.JobDetail;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
//import org.springframework.scheduling.quartz.JobDetailFactoryBean;
//
//import java.util.TimeZone;
//
//import static com.talan.polaris.constants.CommonConstants.*;
///**
// * QuartzSubmitJobs.
// *
// * @author Imen Mechergui
// * @since 1.1.0
// */
//@Configuration
//public class QuartzSubmitJobs {
//
//    @Value("${" + ConfigurationConstants.VALIDATE_REQUESTS_CRON + "}")
//    String cronAutoValidation;
//
//
//    @Bean(name = AUTO_VALIDATION_JOB_BEAN_NAME)
//    public JobDetailFactoryBean jobAutoValidation() {
//        JobDetailFactoryBean jobDetailFactoryBean = QuartzConfig.createJobDetail(AutoValidationJob.class, AUTO_VALIDATION_JOB_NAME);
//        jobDetailFactoryBean.setGroup(JOB_DETAILS_GROUP_NAME);
//        jobDetailFactoryBean.setDescription(JOB_AUTO_VALIDATION_DESCRIPTION);
//        return jobDetailFactoryBean;
//    }
//
//    @Bean(name = AUTO_VALIDATION_TRIGGER_BEAN_NAME)
//    public CronTriggerFactoryBean triggerAutoValidation(@Qualifier(AUTO_VALIDATION_JOB_BEAN_NAME) JobDetail jobDetail) {
//        CronTriggerFactoryBean cronTriggerFactoryBean = QuartzConfig.createCronTrigger(jobDetail, cronAutoValidation, AUTO_VALIDATION_TRIGGER_NAME);
//        cronTriggerFactoryBean.setGroup(TRIGGER_GROUP_NAME);
//        cronTriggerFactoryBean.setDescription(TRIGGER_AUTO_VALIDATION_DESCRIPTION);
//        cronTriggerFactoryBean.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_PARIS));
//        return cronTriggerFactoryBean;
//    }
//}
