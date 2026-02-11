package com.blaqboxdev.unsplash.Services.Implementations;

import com.blaqboxdev.unsplash.Models.DTO.ImageDTO;
import com.blaqboxdev.unsplash.Exceptions.ImageNotFoundException;
import com.blaqboxdev.unsplash.Exceptions.ProfileNotFoundException;
import com.blaqboxdev.unsplash.Models.Entities.Image;
import com.blaqboxdev.unsplash.Models.Entities.Profile;
import com.blaqboxdev.unsplash.Repositories.ImageRepository;
import com.blaqboxdev.unsplash.Repositories.ProfileRepository;
import com.blaqboxdev.unsplash.Services.ImageService;

import com.blaqboxdev.unsplash.utils.ImageUtils;
import com.blaqboxdev.unsplash.utils.ThumbnailGenerator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageServiceImp implements ImageService {

    @Autowired
    final private ImageRepository imageRepository;

    @Autowired
    final private ProfileRepository profileRepository;

    @Autowired
    final private StorageServiceAzure storageService;

    @Override
    public ImageDTO getImageById(String id) {
        Image image = imageRepository.findById(id.trim()).orElseThrow(() -> new ImageNotFoundException("Image with ID %s was not found!".formatted(id)));
        Profile profile = profileRepository.findById(image.getProfile()).orElseThrow(() -> new ProfileNotFoundException("Profile for image not found"));
        return new ImageDTO(image.getLabel(),image.getTags(), image.get_id(), image.getUrl(), image.getDate_added(),Profile.toDTO(profile));
    }

    @Override
    public List<ImageDTO> getAllImages() {
        List<Image> images = imageRepository.findAll();

        return images.stream()
                .map((img) -> {
                    Profile profile = profileRepository.findById(img.getProfile()).orElse(null);
                    return new ImageDTO(img.getLabel(), img.getTags(), img.get_id(), img.getUrl(), img.getDate_added(), Profile.toDTO(profile));
                }).toList();
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
        details.setProfile(uploader.get().get_id());

        // 3. Generate unique image name and upload
        String imageName = UUID.randomUUID().toString() + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().length() - 4);
        String imageUrl = storageService.uploadImage(imageName, file.getInputStream());
        details.setUrl(imageUrl);

        // 4. Set date added
        details.setDate_added(LocalDateTime.now());

        // 5. Save the image
        return imageRepository.save(details);
    }

    @Override
    public List<Image> getImagesByUsername(String username) {
        List<Image> images = imageRepository.findAll();
        Profile profile = profileRepository.findByUsername(username).orElseThrow(() -> new ProfileNotFoundException("Profile with Username not found"));
        return images.stream()
                .filter(image -> image.getProfile().equals(profile.get_id()))
                .toList();
    }


    public Image generateImageThumbnail(String id) throws Exception {
        Image image = imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException("image not found. Failed to generate thumbnail"));

        // ------------------------------
        // 1. Download image to temp file
        // ------------------------------
        Path tempOriginal = ImageUtils.downloadImageToTemp(image.getUrl());
        System.out.println(tempOriginal);

        byte[] thumbBytes = ThumbnailGenerator.createThumbnail(tempOriginal.toFile(), 480, 600);


        // 2. Upload to Azure Blob Storage
        String thumbnailFileName = "thumb_" + image.get_id() + ".jpg";

        String thumbnailUrl = storageService.uploadBytes(
                thumbnailFileName,
                thumbBytes,
                "image/jpeg"
        );

        Files.deleteIfExists(tempOriginal);

        // 3. Save thumbnail URL in MongoDB
        image.setThumbnail(thumbnailUrl);
        return imageRepository.save(image);
    }


}
