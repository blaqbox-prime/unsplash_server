package com.blaqboxdev.unsplash.Controllers;

import com.blaqboxdev.unsplash.Models.Entities.Image;
import com.blaqboxdev.unsplash.Models.Entities.Profile;
import com.blaqboxdev.unsplash.Repositories.ImageRepo;
import com.blaqboxdev.unsplash.Repositories.PhotoRepositoryCustom;
import com.blaqboxdev.unsplash.Repositories.ProfileRepository;
import com.blaqboxdev.unsplash.Repositories.SearchRepo;
import com.blaqboxdev.unsplash.Services.Implementations.StorageServiceAzure;
import com.blaqboxdev.unsplash.Services.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 36000)
@RestController
@RequestMapping("/api/v1/images")
public class ImagesController {

    @Autowired
    final ImageRepo repo;
    @Autowired
    final PhotoRepositoryCustom pcrepo;
    @Autowired
    final SearchRepo srepo;
    @Autowired
    final ProfileRepository profileRepository;
    @Autowired
    final StorageServiceAzure storageService;

    @GetMapping("/")
    public List<Image> allPhotos(){
        return repo.findAll();
    }

    @GetMapping("/latest")
    public List<Image> allPhotosDesc(){
        return pcrepo.findAllSorted();
    }

    @SneakyThrows
    @PostMapping(value = "/", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<?> addPhoto(@RequestPart("details") Image details,
                         @RequestPart("userId") String userId,
                         @RequestPart("image") MultipartFile image){

//        local object
        Image newImage = details;

        Optional<Profile> uploader = profileRepository.findById(userId);
//        if the user is invalid, return 403
        if (uploader.isEmpty() || image.isEmpty()){
            return ResponseEntity.badRequest().body("Image not provided");
        }
        newImage.setUser(uploader.get());

        String imageName = UUID.randomUUID().toString() + image.getOriginalFilename().substring(image.getOriginalFilename().length() - 4);

        String imageUrl = storageService.uploadImage(imageName, image.getInputStream());

        newImage.setUrl(imageUrl);
        newImage.setDate_added(LocalDateTime.now());

        repo.save(newImage);

        return ResponseEntity.ok().body(details);

    }

    @GetMapping("/search")
    public List<Image> search(@RequestParam String text){
        return srepo.findByText(Arrays.stream(text.split(" ")).toList());
    }

}