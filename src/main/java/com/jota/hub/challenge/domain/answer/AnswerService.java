package com.jota.hub.challenge.domain.answer;

import com.jota.hub.challenge.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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

    public Answer update(Answer answer) {
        var answerExists = findById(answer.getId());
        if(!Objects.equals(answerExists.getAuthor().getId(), answer.getAuthor().getId())){
            throw new SecurityException("User can only update their own answers.");
        }
        answerExists.setMessage(answer.getMessage());
        return repository.save(answerExists);
    }

    public void delete(Answer answer) {
        var answerExists = findById(answer.getId());
        if(!Objects.equals(answerExists.getAuthor().getId(), answer.getAuthor().getId())){
            throw new SecurityException("User can only delete their own answers.");
        }

        repository.delete(answerExists);
    }
}
