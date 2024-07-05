package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.course.Course;
import com.jota.hub.challenge.domain.course.CourseService;
import com.jota.hub.challenge.domain.course.dtos.CourseDTO;
import com.jota.hub.challenge.domain.course.dtos.UpdateCourseDTO;
import com.jota.hub.challenge.infra.dto.StandardResponseDTO;
import com.jota.hub.challenge.infra.springdoc.schemas.DefaultErrorResponseSchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Courses", description = "Operations related to courses.")
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/cursos")
@AllArgsConstructor
public class CourseController {

    private CourseService courseService;

    @PostMapping
    @Operation(description = "Course creation.", summary = "Create a new course.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Course created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data provided.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            )
    })
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
    @Operation(description = "List courses.", summary = "Courses listing.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success."
            ),
    })
    public ResponseEntity<StandardResponseDTO<Page<CourseDTO>>> list(@PageableDefault(sort = "id") Pageable pageable){
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Listing courses.",
                true,
                courseService.list(pageable).map(CourseDTO::new)
        ));
    }

    @GetMapping("/{id}")
    @Operation(description = "Course search.", summary = "Search for a course by its id.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            )
    })
    public ResponseEntity<StandardResponseDTO<CourseDTO>> findById(@PathVariable(value = "id") Long id){
        var course = courseService.findById(id);
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Showing found course.",
                true,
                new CourseDTO(course)
        ));
    }

    @PutMapping("/{id}")
    @Operation(description = "Course update.", summary = "Update a course.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data provided",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            )
    })
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
    @Operation(description = "Course delete.", summary = "Delete a course.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Success."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User tries to delete an inactive course.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            )
    })
    public void delete(@PathVariable(value = "id") Long id){
        courseService.delete(id);
    }
}
