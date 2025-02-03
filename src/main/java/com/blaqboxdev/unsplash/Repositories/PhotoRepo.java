package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PhotoRepo extends MongoRepository<Photo,String> {
}
