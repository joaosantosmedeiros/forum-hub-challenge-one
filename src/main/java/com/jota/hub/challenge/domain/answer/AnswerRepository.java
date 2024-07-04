package com.jota.hub.challenge.domain.answer;

import com.jota.hub.challenge.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByAuthor(User author);
}
