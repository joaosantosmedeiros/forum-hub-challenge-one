package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.user.CreateUserDTO;
import com.jota.hub.challenge.domain.user.User;
import com.jota.hub.challenge.domain.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid CreateUserDTO dto) {
        User user = new User(null, dto.name(), dto.email(), dto.password(), null);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
    }
}
