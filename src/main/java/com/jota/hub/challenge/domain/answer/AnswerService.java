package com.jota.hub.challenge.domain.answer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnswerService {

    private AnswerRepository repository;

    public Answer create(Answer answer){
        return repository.save(answer);
    }
}
