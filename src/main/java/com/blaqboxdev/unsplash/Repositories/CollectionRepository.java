package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Entities.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends MongoRepository<Collection, String> {

    public Optional<List<Collection>> findAllCollectionsByAuthor(String username);

}
