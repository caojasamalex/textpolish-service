package com.example.textpolish_service.config;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail fetchSupportedDataJobDetail() {
        return JobBuilder.newJob(com.example.textpolish_service.config.FetchSupportedDataJob.class)
                .withIdentity("fetchSupportedDataJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger fetchSupportedDataJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(fetchSupportedDataJobDetail())
                .withIdentity("fetchSupportedDataJobTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
                .build();
    }
}