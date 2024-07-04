package com.jota.hub.challenge.domain.answer;

import com.jota.hub.challenge.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AnswerService {

    private AnswerRepository repository;

    public Answer create(Answer answer){
        return repository.save(answer);
    }

    public List<Answer> findByAuthor(User author) {
        return repository.findAllByAuthor(author);
    }

    public Answer findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Answer not found."));
    }
}
