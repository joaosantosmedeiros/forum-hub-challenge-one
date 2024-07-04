package com.jota.hub.challenge.domain.course;

import com.jota.hub.challenge.domain.topic.Topic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "COURSES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "course")
    List<Topic> topics;
}
