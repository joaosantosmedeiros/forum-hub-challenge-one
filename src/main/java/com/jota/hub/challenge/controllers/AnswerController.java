package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.answer.Answer;
import com.jota.hub.challenge.domain.answer.AnswerService;
import com.jota.hub.challenge.domain.answer.dtos.AnswerDTO;
import com.jota.hub.challenge.domain.topic.TopicService;
import com.jota.hub.challenge.domain.user.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/respostas")
@AllArgsConstructor
public class AnswerController {

    private AnswerService answerService;
    private TopicService topicService;

    @PostMapping
    public ResponseEntity<AnswerDTO> create(@RequestBody @Valid AnswerDTO dto) {
        var topic = topicService.findById(dto.topicId());
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Answer answer = answerService.create(new Answer(null, dto.message(), topic, LocalDateTime.now(), user));

        return ResponseEntity.status(HttpStatus.CREATED).body(new AnswerDTO(answer));
    }
}
