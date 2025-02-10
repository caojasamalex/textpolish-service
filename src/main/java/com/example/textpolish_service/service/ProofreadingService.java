package com.example.textpolish_service.service;

import com.example.textpolish_service.dto.PolishRequest;
import com.example.textpolish_service.dto.ProofreadingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProofreadingService {

    private final RestTemplate restTemplate;

    @Autowired
    public ProofreadingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String PROOFREADING_SERVICE_URL = "https://proofreading-service.com";

    @Cacheable("languages")
    public List<String> getSupportedLanguages() {
        String url = PROOFREADING_SERVICE_URL + "/languages";

        return Arrays.asList(restTemplate.getForObject(url, String[].class));
    }

    @Cacheable("domains")
    public List<String> getSupportedDomains() {
        String url = PROOFREADING_SERVICE_URL + "/domains";

        return Arrays.asList(restTemplate.getForObject(url, String[].class));
    }

    public ProofreadingResponse proofread(PolishRequest request) {
        String url = PROOFREADING_SERVICE_URL + "/proofread";

        return restTemplate.postForObject(url, request, ProofreadingResponse.class);
    }

    public void fetchAndCacheSupportedLanguages() {
        getSupportedLanguages();
    }

    public void fetchAndCacheSupportedDomains() {
        getSupportedDomains();
    }
}