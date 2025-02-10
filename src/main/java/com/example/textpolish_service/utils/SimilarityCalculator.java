package com.example.textpolish_service.utils;

import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.stereotype.Component;

@Component
public class SimilarityCalculator {

    public double calculate(String text1, String text2) {
        JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();
        return similarity.apply(text1, text2);
    }
}