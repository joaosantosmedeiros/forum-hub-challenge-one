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
    public ResponseEntity<Page<ReturnTopicDTO>> list(@PageableDefault(sort = "creationTime") Pageable pageable){
        return ResponseEntity.ok(topicService.findAll(pageable).map(ReturnTopicDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnTopicDTO> getOne(@PathVariable Long id) {
        var topic = topicService.findById(id);
        return ResponseEntity.ok(new ReturnTopicDTO(topic));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReturnTopicDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateTopicDTO dto) {
        var topic = topicService.update(id, dto.message(), dto.title());
        return ResponseEntity.ok(new ReturnTopicDTO(topic));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        topicService.delete(id);
    }
}
