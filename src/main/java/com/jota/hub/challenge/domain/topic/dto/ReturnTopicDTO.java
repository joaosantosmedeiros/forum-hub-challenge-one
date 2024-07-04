package com.jota.hub.challenge.domain.topic.dto;

import com.jota.hub.challenge.domain.topic.Topic;
import com.jota.hub.challenge.domain.topic.TopicStatus;

import java.time.LocalDateTime;

public record ReturnTopicDTO (Long id, String title, String message, LocalDateTime creationDate, TopicStatus status, boolean isActive, Long authorId, Long courseId){
    public ReturnTopicDTO(Topic topic){
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreationTime(),
                topic.getStatus(),
                topic.isActive(),
                topic.getAuthor().getId(),
                topic.getCourse().getId()
        );
    }
}
