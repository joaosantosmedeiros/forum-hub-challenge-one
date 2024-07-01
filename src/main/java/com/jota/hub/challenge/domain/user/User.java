package com.jota.hub.challenge.domain.user;

import com.jota.hub.challenge.domain.topic.Topic;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "author")
    private List<Topic> topics;
}
