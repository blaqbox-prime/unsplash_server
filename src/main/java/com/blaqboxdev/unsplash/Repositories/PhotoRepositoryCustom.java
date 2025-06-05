package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Entities.Image;

import java.util.List;

public interface PhotoRepositoryCustom {
    public List<Image> findAllSorted();
}
