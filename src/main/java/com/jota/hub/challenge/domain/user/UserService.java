package com.jota.hub.challenge.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User create(User user) {
        var userExists = repository.findByEmail(user.getEmail());
        if(userExists.isPresent()){
            throw new IllegalArgumentException("Email already in use.");
        }

        return repository.save(user);
    }
}
