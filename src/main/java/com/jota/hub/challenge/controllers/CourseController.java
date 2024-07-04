package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.course.Course;
import com.jota.hub.challenge.domain.course.CourseService;
import com.jota.hub.challenge.domain.course.dtos.CourseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cursos")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseDTO> create(@RequestBody @Valid CourseDTO courseDTO) {

        var course = courseService.create(new Course(
                null,
                courseDTO.category(),
                courseDTO.category(),
                courseDTO.isActive(),
                null
        ));

        return ResponseEntity.ok(new CourseDTO(course));
    }
}
