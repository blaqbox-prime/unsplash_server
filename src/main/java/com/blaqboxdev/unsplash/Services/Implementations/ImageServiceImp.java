package com.blaqboxdev.unsplash.Services.Implementations;

import com.blaqboxdev.unsplash.Models.Entities.Image;
import com.blaqboxdev.unsplash.Repositories.ImageRepo;
import com.blaqboxdev.unsplash.Services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageServiceImp implements ImageService {

    @Autowired
    private ImageRepo imageRepository;

    @Override
    public Image getImageById(String id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public List<Image> findByText(String text) {
        return List.of();
    }
}
