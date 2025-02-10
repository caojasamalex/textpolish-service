package com.example.textpolish_service.config;

import com.example.textpolish_service.service.ProofreadingService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FetchSupportedDataJob implements Job {

    @Autowired
    private ProofreadingService proofreadingService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        proofreadingService.fetchAndCacheSupportedLanguages();
        proofreadingService.fetchAndCacheSupportedDomains();
    }
}
