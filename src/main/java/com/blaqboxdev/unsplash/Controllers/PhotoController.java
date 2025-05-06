package com.blaqboxdev.unsplash.Controllers;

import com.blaqboxdev.unsplash.Models.Image;
import com.blaqboxdev.unsplash.Models.Profile;
import com.blaqboxdev.unsplash.Models.UploadImageRequest;
import com.blaqboxdev.unsplash.Repositories.PhotoRepo;
import com.blaqboxdev.unsplash.Repositories.PhotoRepositoryCustom;
import com.blaqboxdev.unsplash.Repositories.ProfileRepository;
import com.blaqboxdev.unsplash.Repositories.SearchRepo;
import com.blaqboxdev.unsplash.Services.StorageService;
import com.sun.jna.platform.win32.Guid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 36000)
@RestController
@RequestMapping("/api")
public class PhotoController {

    @Autowired
    final PhotoRepo repo;
    @Autowired
    final PhotoRepositoryCustom pcrepo;
    @Autowired
    final SearchRepo srepo;
    @Autowired
    final ProfileRepository profileRepository;

    @GetMapping("/photos")
    public List<Image> allPhotos(){
        return repo.findAll();
    }

    @GetMapping("/photos-sorted")
    public List<Image> allPhotosDesc(){
        return pcrepo.findAllSorted();
    }

    //@CrossOrigin
    @SneakyThrows
    @PostMapping(value = "/photos", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Image> addPhoto(@RequestPart("details") Image details,
                         @RequestPart("userId") String userId,
                         @RequestPart("image") MultipartFile image){
//        local object
        Image newImage = details;

        Optional<Profile> uploader = profileRepository.findById(userId);
//        if the user is invalid, return 403
        if (uploader.isEmpty() || image.isEmpty()){
            return ResponseEntity.badRequest().body(null);
        }
        newImage.setUser(uploader.get());

        StorageService storageService = new StorageService();

        String imageName = UUID.randomUUID().toString() + image.getOriginalFilename().substring(image.getOriginalFilename().length() - 4);

        String imageUrl = storageService.uploadImage(imageName, image.getInputStream());

        newImage.setUrl(imageUrl);
        newImage.setDate_added(LocalDateTime.now());

         Image savedImage = repo.save(newImage);

         return ResponseEntity.ok().body(savedImage);

    }

    @GetMapping("/photos/{text}")
    public List<Image> search(@PathVariable String text){
        return srepo.findByText(text);
    }

}