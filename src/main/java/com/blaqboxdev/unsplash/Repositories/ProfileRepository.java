package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProfileRepository extends MongoRepository<Profile, String> {
    Optional<Profile> findByUsername(String username);
}
