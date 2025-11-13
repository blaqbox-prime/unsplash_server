package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Entities.Profile;
import com.blaqboxdev.unsplash.Models.Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends MongoRepository<Profile, String> {
    Optional<Profile> findByUsername(String username);

    // Option 1: Direct find by the referenced User object (Spring Data will use its ID)
    Optional<Profile> findByUser(User user);

    // Option 2: Find by the User's ID explicitly (more direct for MongoDB queries)
    @Query("{ 'user.$id' : ?0 }") // For ObjectId, use ObjectId('?0') or similar if not String
    Optional<Profile> findByUserId(String userId);

}
