package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.topic.*;
import com.jota.hub.challenge.domain.topic.dto.CreateTopicDTO;
import com.jota.hub.challenge.domain.topic.dto.ReturnTopicDTO;
import com.jota.hub.challenge.domain.topic.dto.UpdateTopicDTO;
import com.jota.hub.challenge.domain.user.User;
import com.jota.hub.challenge.domain.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    public ResponseEntity<ReturnTopicDTO> createTopic(@RequestBody @Valid CreateTopicDTO dto) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var topic = new Topic(null, dto.title(), dto.message(), LocalDateTime.now(), TopicStatus.OPEN, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ReturnTopicDTO(topicService.create(topic)));
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
