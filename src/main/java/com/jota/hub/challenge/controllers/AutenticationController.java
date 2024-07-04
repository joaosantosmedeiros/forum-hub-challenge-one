package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.user.User;
import com.jota.hub.challenge.infra.security.TokenService;
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
public class AutenticationController {

    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid LoginDTO dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var authentication = manager.authenticate(authenticationToken);

        var token = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(token);
    }

    record LoginDTO(
            @NotBlank
            @Email
            String email,
            @NotBlank
            String password
    ){}
}
