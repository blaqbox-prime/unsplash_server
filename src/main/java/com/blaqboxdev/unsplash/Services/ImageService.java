package com.blaqboxdev.unsplash.Services;

import com.blaqboxdev.unsplash.Models.Entities.Image;

import java.util.List;

public interface ImageService {
    public Image getImageById(String id);
    public List<Image> getAllImages();
    public List<Image> findByText(String text);
}
