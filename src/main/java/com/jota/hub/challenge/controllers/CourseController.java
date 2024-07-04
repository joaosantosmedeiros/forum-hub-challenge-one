package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.course.Course;
import com.jota.hub.challenge.domain.course.CourseService;
import com.jota.hub.challenge.domain.course.dtos.CourseDTO;
import com.jota.hub.challenge.domain.course.dtos.UpdateCourseDTO;
import com.jota.hub.challenge.infra.dto.StandardResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cursos")
@AllArgsConstructor
public class CourseController {

    private CourseService courseService;

    @PostMapping
    public ResponseEntity<StandardResponseDTO<CourseDTO>> create(@RequestBody @Valid CourseDTO courseDTO) {

        var course = courseService.create(new Course(
                null,
                courseDTO.name(),
                courseDTO.category(),
                courseDTO.isActive(),
                null
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new StandardResponseDTO<>(
                "Course created successfully.",
                true,
                new CourseDTO(course)
        ));
    }

    @GetMapping
    public ResponseEntity<StandardResponseDTO<Page<CourseDTO>>> list(@PageableDefault(sort = "id") Pageable pageable){
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Listing courses.",
                true,
                courseService.list(pageable).map(CourseDTO::new)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponseDTO<CourseDTO>> findById(@PathVariable(value = "id") Long id){
        var course = courseService.findById(id);
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Showing found course.",
                true,
                new CourseDTO(course)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponseDTO<CourseDTO>> update(@PathVariable Long id, @RequestBody UpdateCourseDTO dto) {

        Course course = courseService.update(new Course(id, dto.name(), dto.category(), true, null));
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Course updated successfully.",
                true,
                new CourseDTO(course)
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id){
        courseService.delete(id);
    }
}
