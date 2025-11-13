package com.blaqboxdev.unsplash.Services;

import com.blaqboxdev.unsplash.Models.DTO.ImageDTO;
import com.blaqboxdev.unsplash.Models.Entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
     ImageDTO getImageById(String id);
     List<ImageDTO> getAllImages();
    Image addPhoto(Image details, String userId, MultipartFile image);
    List<Image> getImagesByUsername(String username);
}
