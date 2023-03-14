package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Photo;

import java.util.List;

public interface PhotoRepositoryCustom {
    public List<Photo> findAllSorted();
}
