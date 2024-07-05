package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.answer.Answer;
import com.jota.hub.challenge.domain.answer.AnswerService;
import com.jota.hub.challenge.domain.answer.dto.AnswerDTO;
import com.jota.hub.challenge.domain.answer.dto.UpdateAnswerDTO;
import com.jota.hub.challenge.domain.topic.TopicService;
import com.jota.hub.challenge.domain.user.User;
import com.jota.hub.challenge.domain.user.UserService;
import com.jota.hub.challenge.infra.dto.StandardResponseDTO;
import com.jota.hub.challenge.infra.springdoc.schemas.CreateAnswerSchema;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@SecurityRequirement(name = "bearer-key")
@Tag(name = "Answers", description = "Operations related to answers.")
@RestController
@RequestMapping("/respostas")
@AllArgsConstructor
public class AnswerController {

    private AnswerService answerService;
    private TopicService topicService;
    private UserService userService;

    @PostMapping
    @Operation(description = "Answer creation.", summary = "Create a new answer.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json", schema = @Schema(implementation=CreateAnswerSchema.class))
    ))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Answer created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data provided.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Topic not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            )
    })
    public ResponseEntity<StandardResponseDTO<AnswerDTO>> create(@RequestBody @Valid AnswerDTO dto) {
        var topic = topicService.findById(dto.topicId());
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Answer answer = answerService.create(new Answer(null, dto.message(), topic, LocalDateTime.now(), user));

        return ResponseEntity.status(HttpStatus.CREATED).body(new StandardResponseDTO<>(
                "Answer created successfully.",
                true,
                new AnswerDTO(answer)
        ));
    }

    @GetMapping("/{id}")
    @Operation(description = "Answer search.", summary = "Search for an answer by its id.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Answer not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            )
    })
    public ResponseEntity<StandardResponseDTO<AnswerDTO>> findById(@PathVariable Long id) {
        Answer answer = answerService.findById(id);
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Showing found answer.",
                true,
                new AnswerDTO(answer)
        ));
    }

    @GetMapping("/autor/{id}")
    @Operation(description = "Answers by author.", summary = "Search for an specific user's answers.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Author not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            )
    })
    public ResponseEntity<StandardResponseDTO<List<AnswerDTO>>> findByAuthor(@PathVariable Long id){
        User author = userService.findById(id);
        List<Answer> answers = answerService.findByAuthor(author);

        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Showing responses by author.",
                true,
                answers.stream().map(AnswerDTO::new).toList()
        ));
    }

    @PutMapping("/{id}")
    @Operation(description = "Answers update.", summary = "Update an answer.")
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
                    responseCode = "401",
                    description = "User tries to update another user's answer.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Answer not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            )
    })
    public ResponseEntity<StandardResponseDTO<AnswerDTO>> update(@PathVariable Long id, @RequestBody @Valid UpdateAnswerDTO dto){
        User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Answer answer = answerService.update(new Answer(id, dto.message(), null, null, author));
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Answer updated successfully",
                true,
                new AnswerDTO(answer))
        );
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Answers delete.", summary = "Delete an answer.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Success."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User tries to delete another user's answer.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Answer not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            )
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Answer answer = new Answer(id, null, null, null, author);
        answerService.delete(answer);
    }
}
