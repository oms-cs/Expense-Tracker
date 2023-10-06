package com.springbooot.tutorials.springmongodbdemo.repository;

import com.springbooot.tutorials.springmongodbdemo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    public Optional<User> findByUsername(String username);
}
