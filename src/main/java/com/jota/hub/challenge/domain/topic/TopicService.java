package com.jota.hub.challenge.domain.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public Topic create(Topic topic) {
        if(!topic.getCourse().isActive()){
            throw new IllegalArgumentException("The topic's course must be active");
        }
        return topicRepository.save(topic);
    }

    public Page<Topic> findAll(Pageable pageable) {
        return topicRepository.findAll(pageable);
    }

    public Topic findById(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Topic not found."));
    }


    public Topic update(Long id, String message, String title) {
        Topic topic = findById(id);

        if(!topic.isActive()){
            throw new IllegalArgumentException("Topic must be active.");
        }

        if(message != null && !message.isBlank()){
            topic.setMessage(message);
        }

        if(title != null && !title.isBlank()){
            topic.setTitle(title);
        }

        return topicRepository.save(topic);
    }

    public void delete(Long id) {
        var topic = findById(id);

        if(!topic.isActive()){
            throw new IllegalArgumentException("The topic is already inactive.");
        }

        topic.setActive(false);
        topicRepository.save(topic);
    }
}
