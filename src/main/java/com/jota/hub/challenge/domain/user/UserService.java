package com.jota.hub.challenge.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(User user) {
        var userExists = repository.findByEmail(user.getEmail());
        if(userExists.isPresent()){
            throw new IllegalArgumentException("Email already in use.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public User findByEmail(String email){
        var user = repository.findByEmail(email);
        if(user.isEmpty()){
            throw new NoSuchElementException("User does not exists.");
        }

        return user.get();
    }
}
