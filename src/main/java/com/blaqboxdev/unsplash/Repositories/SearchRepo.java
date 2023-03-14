package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Photo;
import org.bson.Document;

import java.util.List;

public interface SearchRepo {
    List<Photo> findByText(String text);
}
