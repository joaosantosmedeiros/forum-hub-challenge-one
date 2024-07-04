package com.jota.hub.challenge.domain.user.dto;

import com.jota.hub.challenge.domain.user.User;

public record ReturnUserDTO(
        Long id,
        String name,
        String email,
        Boolean isActive
) {
    public ReturnUserDTO(User user){
        this(user.getId(), user.getName(), user.getEmail(), user.getIsActive());
    }
}
