package com.jota.hub.challenge.domain.topic;

import com.jota.hub.challenge.domain.user.User;

public record TopicDTO(
        String message,
        String title,
        User author
) {
}
