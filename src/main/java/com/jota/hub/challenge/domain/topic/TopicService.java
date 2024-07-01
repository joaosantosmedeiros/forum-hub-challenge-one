package com.jota.hub.challenge.domain.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public Topic create(TopicDTO topicDTO) {
        Topic topic = new Topic(null, topicDTO.title(), topicDTO.message(), LocalDateTime.now(), TopicStatus.OPEN, topicDTO.author());
        return topicRepository.save(topic);
    }

    public Page<Topic> findAll(Pageable pageable) {
        return topicRepository.findAll(pageable);
    }
}
