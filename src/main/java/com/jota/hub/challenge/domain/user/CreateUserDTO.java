package com.jota.hub.challenge.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(
        @NotBlank
        String name,
        @NotBlank
        String password,
        @NotBlank
        @Email
        String email
) {
}
