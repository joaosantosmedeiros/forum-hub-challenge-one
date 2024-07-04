package com.jota.hub.challenge.domain.answer.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateAnswerDTO (@NotBlank String message){}
