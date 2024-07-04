package com.jota.hub.challenge.infra.dto;

public record StandardResponseDTO<T>(
        String message,
        Boolean status,
        T data
) {}
