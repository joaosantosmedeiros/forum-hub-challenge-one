package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.user.User;
import com.jota.hub.challenge.domain.user.UserService;
import com.jota.hub.challenge.domain.user.dto.CreateUserDTO;
import com.jota.hub.challenge.domain.user.dto.ReturnUserDTO;
import com.jota.hub.challenge.domain.user.dto.UpdateUserDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Users", description = "Operations related to users.")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    @Operation(description = "User creation.", summary = "Create a new user.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data provided.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            ),
    })
    public ResponseEntity<StandardResponseDTO<ReturnUserDTO>> create(@RequestBody @Valid CreateUserDTO dto) {
        User user = new User(null, dto.name(), dto.email(), dto.password(), true, null);

        return ResponseEntity.status(HttpStatus.CREATED).body(new StandardResponseDTO<>(
                "User created successfully.",
                true,
                new ReturnUserDTO(userService.create(user))
        ));
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping
    @Operation(description = "List users.", summary = "List for users.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success."
            ),
    })
    public ResponseEntity<StandardResponseDTO<Page<ReturnUserDTO>>> list(@PageableDefault(sort = "name") Pageable pageable){
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Listing users.",
                true,
                userService.list(pageable).map(ReturnUserDTO::new)
        ));
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/{id}")
    @Operation(description = "User search.", summary = "Search for an user by its id.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            )
    })
    public ResponseEntity<StandardResponseDTO<ReturnUserDTO>> findById(@PathVariable(value = "id") Long id) {
        var user = userService.findById(id);
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Showing found user.",
                true,
                new ReturnUserDTO(user)
        ));
    }

    @SecurityRequirement(name = "bearer-key")
    @PutMapping("/{id}")
    @Operation(description = "User update.", summary = "Update an user.")
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
                    description = "User tries to update another user.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            )
    })
    public ResponseEntity<StandardResponseDTO<ReturnUserDTO>> update(@PathVariable Long id, @RequestBody UpdateUserDTO dto) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!Objects.equals(user.getId(), id)){
            throw new SecurityException("User can only update itself.");
        }
        User updatedUser = userService.update(new User(id, dto.name(), dto.email(), dto.password(), true, null));
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "User updated successfully",
                true,
                new ReturnUserDTO(updatedUser)
        ));
    }

    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "User delete.", summary = "Delete an user.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Success."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User tries to delete another user.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            )
    })
    public void delete(@PathVariable Long id){
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!Objects.equals(user.getId(), id)){
            throw new SecurityException("User can only delete itself.");
        }
        userService.delete(id);
    }
}
