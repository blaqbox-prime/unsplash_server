package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Entities.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends MongoRepository<Collection, String> {

     Optional<List<Collection>> findAllCollectionsByAuthor(String username);
     Optional<Collection> findBy_id(String id);

    @Query("{'images': {'$in': [ObjectId('?0')]}}")
    Optional<List<Collection>> findAllByImageId(String id);
}
