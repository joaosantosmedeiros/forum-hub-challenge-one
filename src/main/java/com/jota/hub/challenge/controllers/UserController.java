package com.jota.hub.challenge.controllers;

import com.jota.hub.challenge.domain.user.User;
import com.jota.hub.challenge.domain.user.UserService;
import com.jota.hub.challenge.domain.user.dto.CreateUserDTO;
import com.jota.hub.challenge.domain.user.dto.ReturnUserDTO;
import com.jota.hub.challenge.domain.user.dto.UpdateUserDTO;
import com.jota.hub.challenge.infra.dto.StandardResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<StandardResponseDTO<ReturnUserDTO>> create(@RequestBody @Valid CreateUserDTO dto) {
        User user = new User(null, dto.name(), dto.email(), dto.password(), true, null);

        return ResponseEntity.status(HttpStatus.CREATED).body(new StandardResponseDTO<>(
                "User created successfully.",
                true,
                new ReturnUserDTO(userService.create(user))
        ));
    }

    @GetMapping
    public ResponseEntity<StandardResponseDTO<Page<ReturnUserDTO>>> list(@PageableDefault(sort = "name") Pageable pageable){
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Listing users.",
                true,
                userService.list(pageable).map(ReturnUserDTO::new)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponseDTO<ReturnUserDTO>> findById(@PathVariable(value = "id") Long id) {
        var user = userService.findById(id);
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "Showing found user.",
                true,
                new ReturnUserDTO(user)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponseDTO<ReturnUserDTO>> update(@PathVariable Long id, @RequestBody UpdateUserDTO dto) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getId() != id){
            throw new SecurityException("User can only update itself.");
        }
        User updatedUser = userService.update(new User(id, dto.name(), dto.email(), dto.password(), true, null));
        return ResponseEntity.ok(new StandardResponseDTO<>(
                "User updated successfully",
                true,
                new ReturnUserDTO(updatedUser)
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getId() != id){
            throw new SecurityException("User can only delete itself.");
        }
        userService.delete(id);
    }
}
