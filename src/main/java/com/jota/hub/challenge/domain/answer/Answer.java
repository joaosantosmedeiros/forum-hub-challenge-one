package com.jota.hub.challenge.domain.answer;

import com.jota.hub.challenge.domain.topic.Topic;
import com.jota.hub.challenge.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ANSWERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "topicId", nullable = false)
    private Topic topic;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "authorId", nullable = false)
    private User author;
}
