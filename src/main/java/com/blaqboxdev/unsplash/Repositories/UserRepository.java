package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
}
