package com.jota.hub.challenge.domain.topic;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TopicService {

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


    public Topic update(Topic topic) {
        Topic topicExists = findById(topic.getId());

        if(!Objects.equals(topic.getAuthor().getId(), topicExists.getAuthor().getId())){
            throw new SecurityException("User can only update their own topics.");
        }

        if(!topicExists.isActive()){
            throw new IllegalArgumentException("Topic must be active.");
        }

        if(topic.getMessage() != null && !topic.getMessage().isBlank()){
            topicExists.setMessage(topic.getMessage());
        }

        if(topic.getTitle() != null && !topic.getTitle().isBlank()){
            topicExists.setTitle(topic.getTitle());
        }

        return topicRepository.save(topicExists);
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
