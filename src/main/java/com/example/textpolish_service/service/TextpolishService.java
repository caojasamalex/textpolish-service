package com.example.textpolish_service.service;

import com.example.textpolish_service.dto.PolishRequest;
import com.example.textpolish_service.dto.PolishResponse;
import com.example.textpolish_service.dto.ProofreadingResponse;
import com.example.textpolish_service.utils.SimilarityCalculator;
import com.example.textpolish_service.utils.TagRemover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TextpolishService {

    @Autowired
    private ProofreadingService proofreadingService;

    @Autowired
    private SimilarityCalculator similarityCalculator;

    @Autowired
    private TagRemover tagRemover;

    public PolishResponse processRequest(PolishRequest request) {
        if (!proofreadingService.getSupportedLanguages().contains(request.getLanguage())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported language");
        }

        if (!proofreadingService.getSupportedDomains().contains(request.getDomain())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported domain");
        }

        ProofreadingResponse proofreadingResponse = proofreadingService.proofread(request);

        double similarity = similarityCalculator.calculate(request.getContent(), proofreadingResponse.getProofread());

        return new PolishResponse(proofreadingResponse.getProofread(), similarity);
    }
}