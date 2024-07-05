package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.user.User;
import com.jota.hub.challenge.infra.dto.StandardResponseDTO;
import com.jota.hub.challenge.infra.security.TokenService;
import com.jota.hub.challenge.infra.springdoc.schemas.DefaultErrorResponseSchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
@Tag(name = "Authentication", description = "Authentication related operations.")
public class AuthenticationController {

    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(description = "Login", summary = "User login.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data provided.",
                    content = @Content(mediaType = "aplication/json", schema = @Schema(implementation = DefaultErrorResponseSchema.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Invalid credentials."
            )
    })
    public ResponseEntity<StandardResponseDTO<String>> login(@RequestBody @Valid LoginDTO dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var authentication = manager.authenticate(authenticationToken);

        var token = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Success.",
                true,
                "Bearer " + token
        ));
    }

    public record LoginDTO(
            @NotBlank
            @Email
            String email,
            @NotBlank
            String password
    ){}
}
