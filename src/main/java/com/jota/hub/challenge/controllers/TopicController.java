package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.course.CourseService;
import com.jota.hub.challenge.domain.topic.Topic;
import com.jota.hub.challenge.domain.topic.TopicService;
import com.jota.hub.challenge.domain.topic.TopicStatus;
import com.jota.hub.challenge.domain.topic.dto.CreateTopicDTO;
import com.jota.hub.challenge.domain.topic.dto.ReturnTopicDTO;
import com.jota.hub.challenge.domain.topic.dto.UpdateTopicDTO;
import com.jota.hub.challenge.domain.user.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class TopicController {

    private TopicService topicService;
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<ReturnTopicDTO> createTopic(@RequestBody @Valid CreateTopicDTO dto) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var course = courseService.findById(dto.courseId());
        var topic = new Topic(null, dto.title(), dto.message(), LocalDateTime.now(), TopicStatus.OPEN, true, user, course);
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

//    TODO fazer checagem se o usuario quer atualizar o topico de outra pessoa
    @PutMapping("/{id}")
    public ResponseEntity<ReturnTopicDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateTopicDTO dto) {
        var topic = topicService.update(id, dto.message(), dto.title());
        return ResponseEntity.ok(new ReturnTopicDTO(topic));
    }

//    TODO fazer checagem se o usuario quer deletar o topico de outra pessoa
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        topicService.delete(id);
    }
}
