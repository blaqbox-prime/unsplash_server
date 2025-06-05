package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Entities.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepo extends MongoRepository<Image,String> {
}
