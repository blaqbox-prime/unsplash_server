package com.blaqboxdev.unsplash.Controllers;

import com.blaqboxdev.unsplash.Models.Entities.Image;
import com.blaqboxdev.unsplash.Models.Entities.Profile;
import com.blaqboxdev.unsplash.Repositories.ImageRepo;
import com.blaqboxdev.unsplash.Repositories.PhotoRepositoryCustom;
import com.blaqboxdev.unsplash.Repositories.ProfileRepository;
import com.blaqboxdev.unsplash.Repositories.SearchRepo;
import com.blaqboxdev.unsplash.Services.ImageService;
import com.blaqboxdev.unsplash.Services.Implementations.StorageServiceAzure;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    final ImageService imageService;

    @Autowired
    final PhotoRepositoryCustom customImageRepository;
    @Autowired
    final SearchRepo searchImageRepository;
    @Autowired
    final ProfileRepository profileRepository;
    @Autowired
    final StorageServiceAzure storageService;

    @GetMapping()
    public ResponseEntity<List<Image>> allPhotos(){
        return ResponseEntity.ok().body(imageService.getAllImages());
    }

    @GetMapping("/latest")
    public List<Image> allPhotosDesc(){
        return customImageRepository.findAllSorted();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@PathVariable String id){
        try {
            return ResponseEntity.ok().body(imageService.getImageById(id));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @SneakyThrows
    @PostMapping(value = "/", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<?> addPhoto(@RequestPart("details") Image details,
                         @RequestPart("userId") String userId,
                         @RequestPart("image") MultipartFile image){

        Image savedImage = imageService.addPhoto(details, userId, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);

    }

    @GetMapping("/search")
    public List<Image> search(@RequestParam String query){
        System.out.println(query);
        return searchImageRepository.findByText(Arrays.stream(query.split(" ")).toList());
    }

}