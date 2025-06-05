package com.blaqboxdev.unsplash.Services;

import com.blaqboxdev.unsplash.Models.Entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    public Image getImageById(String id);
    public List<Image> getAllImages();
    public List<Image> findByText(String text);
    Image addPhoto(Image details, String userId, MultipartFile image);
}
