package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.topic.Topic;
import com.jota.hub.challenge.domain.topic.CreateTopicDTO;
import com.jota.hub.challenge.domain.topic.TopicService;
import com.jota.hub.challenge.domain.topic.UpdateTopicDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody @Valid CreateTopicDTO dto) {
        var topic = topicService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }

    @GetMapping
    public ResponseEntity<Page<Topic>> list(@PageableDefault(sort = "creationTime") Pageable pageable){
        return ResponseEntity.ok(topicService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topic> update(@PathVariable Long id, @RequestBody @Valid UpdateTopicDTO dto) {
        return ResponseEntity.ok(topicService.update(id, dto.message(), dto.title()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        topicService.delete(id);
    }
}