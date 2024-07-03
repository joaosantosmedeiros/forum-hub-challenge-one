package com.jota.hub.challenge.domain.topic;

import com.jota.hub.challenge.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public Topic create(Topic topic) {
        return topicRepository.save(topic);
    }

    public Page<Topic> findAll(Pageable pageable) {
        return topicRepository.findAll(pageable);
    }

    public Topic findById(Long id) {
        return topicRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }


    public Topic update(Long id, String message, String title) {
        Topic topic = findById(id);

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
        topicRepository.delete(topic);
    }
}
