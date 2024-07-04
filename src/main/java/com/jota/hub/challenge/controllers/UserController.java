package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.user.CreateUserDTO;
import com.jota.hub.challenge.domain.user.User;
import com.jota.hub.challenge.domain.user.UserService;
import com.jota.hub.challenge.domain.user.dto.ReturnUserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ReturnUserDTO> create(@RequestBody @Valid CreateUserDTO dto) {
        User user = new User(null, dto.name(), dto.email(), dto.password(), true, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ReturnUserDTO(userService.create(user)));
    }

    @GetMapping
    public ResponseEntity<Page<ReturnUserDTO>> list(@PageableDefault(sort = "name") Pageable pageable){
        return ResponseEntity.ok(userService.list(pageable).map(ReturnUserDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnUserDTO> findById(@PathVariable(value = "id") Long id) {
        var user = userService.findById(id);
        return ResponseEntity.ok(new ReturnUserDTO(user));
    }
}
