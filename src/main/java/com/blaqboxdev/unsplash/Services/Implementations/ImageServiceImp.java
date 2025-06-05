package com.blaqboxdev.unsplash.Services.Implementations;

import com.blaqboxdev.unsplash.Models.Entities.Image;
import com.blaqboxdev.unsplash.Models.Entities.Profile;
import com.blaqboxdev.unsplash.Repositories.ImageRepo;
import com.blaqboxdev.unsplash.Repositories.ProfileRepository;
import com.blaqboxdev.unsplash.Services.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageServiceImp implements ImageService {

    @Autowired
    final private ImageRepo imageRepository;

    @Autowired
    final private ProfileRepository profileRepository;

    @Autowired
    final private StorageServiceAzure storageService;

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

    @Override
    @SneakyThrows
    public Image addPhoto(Image details, String userId, MultipartFile file) {
        Optional<Profile> uploader = profileRepository.findById(userId);
        if (uploader.isEmpty() || file.isEmpty()) {
            // In a service, you typically throw an exception rather than returning ResponseEntity.
            // The controller will then catch this exception and map it to an appropriate HTTP status.
            throw new IllegalArgumentException("Invalid user ID or image not provided.");
        }

        // 2. Set profile for the new image
        details.setProfile(uploader.get());

        // 3. Generate unique image name and upload
        String imageName = UUID.randomUUID().toString() + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().length() - 4);
        String imageUrl = storageService.uploadImage(imageName, file.getInputStream());
        details.setUrl(imageUrl);

        // 4. Set date added
        details.setDate_added(LocalDateTime.now());

        // 5. Save the image
        return imageRepository.save(details);
    }
}
