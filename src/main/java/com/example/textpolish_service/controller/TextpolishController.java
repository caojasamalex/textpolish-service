package com.example.textpolish_service.controller;

import com.example.textpolish_service.dto.PolishRequest;
import com.example.textpolish_service.dto.PolishResponse;
import com.example.textpolish_service.service.TextpolishService;
import com.example.textpolish_service.utils.TagRemover;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/polish")
public class TextpolishController {
    @Autowired
    private TextpolishService textpolishService;

    @PostMapping
    public ResponseEntity<?> polishText(@RequestBody @Valid PolishRequest request) {
        request.setContent(TagRemover.removeTags(request.getContent()));

        if(countWords(request.getContent()) == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content is required");
        } else if (countWords(request.getContent()) > 30) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content exceeds 30 words");
        }

        PolishResponse response = textpolishService.processRequest(request);
        return ResponseEntity.ok(response);
    }

    private int countWords(String content) {
        if (content == null || content.trim().isEmpty()) {
            return 0;
        }
        String[] words = content.split("\\s+");
        return words.length;
    }
}
