package com.jota.hub.challenge.domain.topic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTopicDTO(
        @NotBlank
        String message,
        @NotBlank
        String title,
        @NotNull
        Long courseId
) {}
