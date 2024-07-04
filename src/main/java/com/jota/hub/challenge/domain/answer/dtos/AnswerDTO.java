package com.jota.hub.challenge.domain.answer.dtos;

import com.jota.hub.challenge.domain.answer.Answer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AnswerDTO(
        Long id,

        @NotBlank
        String message,

        @NotNull
        Long topicId,

        Long authorId,

        LocalDateTime creationDate
        ) {
        public AnswerDTO(Answer answer) {
                this(answer.getId(), answer.getMessage(), answer.getTopic().getId(), answer.getAuthor().getId(), answer.getCreationDate());
        }
}
