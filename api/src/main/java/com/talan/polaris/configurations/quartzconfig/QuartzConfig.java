//package com.talan.polaris.configurations.quartzconfig;
//
//import org.apache.commons.lang3.ArrayUtils;
//import org.quartz.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.quartz.*;
//
//import javax.sql.DataSource;
//import java.util.Calendar;
//import java.util.Properties;
//import java.util.TimeZone;
//
//import static com.talan.polaris.constants.CommonConstants.TIME_ZONE_PARIS;
//
///**
// * Quartz Config.
// *
// * @author Imen Mechergui
// * @since 1.1.0
// */
//@Configuration
//public class QuartzConfig {
//    private ApplicationContext applicationContext;
//    private DataSource dataSource;
//    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzConfig.class);
//
//    public QuartzConfig(ApplicationContext applicationContext, DataSource dataSource) {
//        this.applicationContext = applicationContext;
//        this.dataSource = dataSource;
//    }
//
//    @Bean
//    public SpringBeanJobFactory springBeanJobFactory() {
//        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
//        jobFactory.setApplicationContext(applicationContext);
//        return jobFactory;
//    }
//
//    @Bean
//    public SchedulerFactoryBean scheduler(Trigger... triggers) {
//        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
//
//        Properties properties = new Properties();
//        properties.setProperty("org.quartz.scheduler.instanceName", "QuartzSerius");
//        properties.setProperty("org.quartz.scheduler.instanceId", "Instance1");
//        properties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
//        schedulerFactory.setOverwriteExistingJobs(true);
//        schedulerFactory.setAutoStartup(true);
//        schedulerFactory.setQuartzProperties(properties);
//        schedulerFactory.setDataSource(dataSource);
//        schedulerFactory.setJobFactory(springBeanJobFactory());
//        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
//        if (ArrayUtils.isNotEmpty(triggers)) {
//            schedulerFactory.setTriggers(triggers);
//        }
//
//        return schedulerFactory;
//    }
//
//    static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs, String triggerName) {
//        LOGGER.info("createTrigger(jobDetail={}, pollFrequencyMs={}, triggerName={})", jobDetail.toString(), pollFrequencyMs, triggerName);
//        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
//        factoryBean.setJobDetail(jobDetail);
//        factoryBean.setStartDelay(0L);
//        factoryBean.setRepeatInterval(pollFrequencyMs);
//        factoryBean.setName(triggerName);
//        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
//        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
//        return factoryBean;
//    }
//
//    static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression, String triggerName) {
//        LOGGER.info("createCronTrigger(jobDetail={}, cronExpression={}, triggerName={})", jobDetail.toString(), cronExpression, triggerName);
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        calendar.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_PARIS));
//        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
//        factoryBean.setJobDetail(jobDetail);
//        factoryBean.setCronExpression(cronExpression);
//        factoryBean.setStartTime(calendar.getTime());
//        factoryBean.setStartDelay(0L);
//        factoryBean.setName(triggerName);
//        factoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
//        return factoryBean;
//    }
//
//    static JobDetailFactoryBean createJobDetail(Class jobClass, String jobName) {
//        LOGGER.info("createJobDetail(jobClass={}, jobName={})", jobClass.getName(), jobName);
//        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
//        factoryBean.setName(jobName);
//        factoryBean.setJobClass(jobClass);
//        factoryBean.setDurability(true);
//
//        return factoryBean;
//    }
//}
