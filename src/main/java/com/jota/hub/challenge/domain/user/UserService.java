package com.jota.hub.challenge.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<User> list(Pageable pageable){
        return repository.findAll(pageable);
    }

    public User findByEmail(String email){
        var user = repository.findByEmail(email);
        if(user.isEmpty()){
            throw new NoSuchElementException("User does not exists.");
        }

        return user.get();
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("User does not exists."));
    }

    public void delete(Long id){
        var user = findById(id);
        if(!user.getIsActive()){
            throw new IllegalArgumentException("The user is already inactive.");
        }

        user.setIsActive(false);
        repository.save(user);
    }
}
