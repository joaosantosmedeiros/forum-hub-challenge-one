package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.answer.Answer;
import com.jota.hub.challenge.domain.answer.AnswerService;
import com.jota.hub.challenge.domain.answer.dtos.AnswerDTO;
import com.jota.hub.challenge.domain.topic.TopicService;
import com.jota.hub.challenge.domain.user.User;
import com.jota.hub.challenge.domain.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/respostas")
@AllArgsConstructor
public class AnswerController {

    private AnswerService answerService;
    private TopicService topicService;
    private UserService userService;

    @PostMapping
    public ResponseEntity<AnswerDTO> create(@RequestBody @Valid AnswerDTO dto) {
        var topic = topicService.findById(dto.topicId());
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Answer answer = answerService.create(new Answer(null, dto.message(), topic, LocalDateTime.now(), user));

        return ResponseEntity.status(HttpStatus.CREATED).body(new AnswerDTO(answer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerDTO> findById(@PathVariable Long id) {
        Answer answer = answerService.findById(id);
        return ResponseEntity.ok(new AnswerDTO(answer));
    }

    @GetMapping("/autor/{id}")
    public ResponseEntity<List<AnswerDTO>> findByAuthor(@PathVariable Long id){
        User author = userService.findById(id);
        List<Answer> answers = answerService.findByAuthor(author);

        return ResponseEntity.ok(answers.stream().map(AnswerDTO::new).toList());
    }
}
