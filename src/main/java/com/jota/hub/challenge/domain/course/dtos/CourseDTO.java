package com.jota.hub.challenge.domain.course.dtos;

import com.jota.hub.challenge.domain.course.Course;
import jakarta.validation.constraints.NotBlank;

public record CourseDTO(
        Long id,
        @NotBlank
        String name,
        @NotBlank
        String category,
        boolean isActive
) {
    public CourseDTO(Course course){
        this(course.getId(), course.getName(), course.getCategory(), course.isActive());
    }
}
