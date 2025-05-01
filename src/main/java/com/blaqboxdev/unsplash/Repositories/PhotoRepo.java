package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepo extends MongoRepository<Image,String> {
}
