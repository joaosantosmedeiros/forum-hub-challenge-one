package com.jota.hub.challenge.domain.topic.dto;

import com.jota.hub.challenge.domain.user.User;
import jakarta.validation.constraints.NotBlank;

public record CreateTopicDTO(
        @NotBlank
        String message,
        @NotBlank
        String title
) {}
