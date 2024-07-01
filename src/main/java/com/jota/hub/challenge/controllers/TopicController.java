package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.topic.Topic;
import com.jota.hub.challenge.domain.topic.TopicDTO;
import com.jota.hub.challenge.domain.topic.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topicos")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody @Valid TopicDTO topicDTO) {
        var topic = topicService.create(topicDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }
}
