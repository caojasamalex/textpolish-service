package com.example.textpolish_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PolishRequest {
    @NotBlank(message = "Language is required")
    private String language;

    @NotBlank(message = "Domain is required")
    private String domain;

    @NotBlank(message = "Content is required")
    private String content;
}
