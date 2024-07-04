package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.course.CourseService;
import com.jota.hub.challenge.domain.topic.Topic;
import com.jota.hub.challenge.domain.topic.TopicService;
import com.jota.hub.challenge.domain.topic.TopicStatus;
import com.jota.hub.challenge.domain.topic.dto.CreateTopicDTO;
import com.jota.hub.challenge.domain.topic.dto.ReturnTopicDTO;
import com.jota.hub.challenge.domain.topic.dto.UpdateTopicDTO;
import com.jota.hub.challenge.domain.user.User;
import com.jota.hub.challenge.infra.dto.StandardResponseDTO;
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
    public ResponseEntity<StandardResponseDTO<ReturnTopicDTO>> createTopic(@RequestBody @Valid CreateTopicDTO dto) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var course = courseService.findById(dto.courseId());
        var topic = new Topic(null, dto.title(), dto.message(), LocalDateTime.now(), TopicStatus.OPEN, true, user, course);

        return ResponseEntity.status(HttpStatus.CREATED).body(new StandardResponseDTO<>(
                "Topic created successfully.",
                true,
                new ReturnTopicDTO(topicService.create(topic)
        )));
    }

    @GetMapping
    public ResponseEntity<StandardResponseDTO<Page<ReturnTopicDTO>>> list(@PageableDefault(sort = "creationTime") Pageable pageable){
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Listing topics.",
                true,
                topicService.findAll(pageable).map(ReturnTopicDTO::new)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponseDTO<ReturnTopicDTO>> getOne(@PathVariable Long id) {
        var topic = topicService.findById(id);
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Showing found topic.",
                true,
                new ReturnTopicDTO(topic)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponseDTO<ReturnTopicDTO>> update(@PathVariable Long id, @RequestBody @Valid UpdateTopicDTO dto) {
        User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Topic topic = new Topic(id, dto.title(), dto.message(), null, null, true, author, null);

        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Topic updated successfully.",
                true,
                new ReturnTopicDTO(topicService.update(topic))
        ));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Topic topic = new Topic(id, null, null, null, null, true, author, null);
        topicService.delete(topic);
    }
}
